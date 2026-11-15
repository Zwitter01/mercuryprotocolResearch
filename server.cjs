const http = require('http');
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');
const os = require('os');
const dns = require('dns');

try {
  dns.setDefaultResultOrder('ipv4first');
} catch (e) {}

const PORT = 3300;
const PUBLIC_DIR = __dirname;

// SONiC Connect Multi-Device State
const connectedDevices = new Map();
const pendingCommands = new Map();

function getLocalNetworkIp() {
  const nets = os.networkInterfaces();
  for (const name of Object.keys(nets)) {
    for (const net of nets[name]) {
      if (net.family === 'IPv4' && !net.internal) {
        return net.address;
      }
    }
  }
  return 'localhost';
}

function cleanStaleDevices() {
  const now = Date.now();
  for (const [id, dev] of connectedDevices.entries()) {
    if (now - dev.lastSeen > 16000) {
      connectedDevices.delete(id);
      pendingCommands.delete(id);
    }
  }
}

const MIME_TYPES = {
  '.html': 'text/html',
  '.css': 'text/css',
  '.js': 'application/javascript',
  '.json': 'application/json',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon',
  '.mp4': 'video/mp4',
  '.mp3': 'audio/mpeg',
  '.m4a': 'audio/mp4'
};

function decryptSaavnMediaUrl(encUrl) {
  try {
    const key = Buffer.from('383465913834659138346591', 'utf8');
    const decipher = crypto.createDecipheriv('des-ede3-ecb', key, null);
    decipher.setAutoPadding(true);
    let dec = decipher.update(encUrl, 'base64', 'utf8') + decipher.final('utf8');
    return dec.replace('_96.mp4', '_320.mp4');
  } catch (err) {
    return null;
  }
}

const fullSongCache = new Map();
const SC_CLIENT_IDS = [
  'qLcgHhXgeEFFaSolTB9I71LBsqncsD93',
  'b0m6uF3e27X29Z3z1wL7e8P9q0R1s2T3'
];
let activeScClientId = SC_CLIENT_IDS[0];

function hasCJK(str) {
  return /[\u3040-\u30ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff\uff66-\uff9f]/.test(str);
}

function extractQueryVariants(raw) {
  if (!raw) return [];
  const list = [raw.trim()];
  const cleaned = raw
    .replace(/\s*\(From "[^"]*"\)/gi, '')
    .replace(/\s*\(feat\.[^)]*\)/gi, '')
    .replace(/\s*\[[^\]]*\]/gi, '')
    .trim();
  if (cleaned && !list.includes(cleaned)) list.push(cleaned);

  // If compound title with dash, slash, or colon
  if (/[-–—\/:|]/.test(raw)) {
    const parts = raw.split(/[-–—\/:|]/).map(p => p.trim()).filter(p => p.length > 1);
    parts.forEach(p => {
      if (!list.includes(p)) list.push(p);
    });
  }

  return list;
}

async function resolveFullSong(query) {
  const cacheKey = (query || '').toLowerCase().trim();
  if (fullSongCache.has(cacheKey)) {
    return fullSongCache.get(cacheKey);
  }

  const variants = extractQueryVariants(query);
  const isCjk = hasCJK(query);

  // Tier 1: JioSaavn 320kbps Studio Master (Fastest, ~300ms, 320kbps)
  const saavnVariants = [...variants].sort((a, b) => {
    const aCjk = hasCJK(a);
    const bCjk = hasCJK(b);
    if (aCjk && !bCjk) return 1;
    if (!aCjk && bCjk) return -1;
    return b.length - a.length;
  });

  for (const v of saavnVariants) {
    if (hasCJK(v) && !/[a-zA-Z0-9]/.test(v)) continue;

    try {
      const saavnUrl = 'https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=5&p=1&q=' + encodeURIComponent(v);
      const res = await fetch(saavnUrl, {
        headers: { 'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36' },
        signal: AbortSignal.timeout(2500)
      });
      const data = await res.json();
      if (data.results && data.results.length > 0) {
        const exact = data.results.find(s => s.song.toLowerCase().trim() === v.toLowerCase().trim());
        const selected = exact || data.results[0];
        const streamUrl = decryptSaavnMediaUrl(selected.encrypted_media_url);
        if (streamUrl) {
          const songObj = {
            success: true,
            provider: 'jiosaavn-320k',
            title: selected.song,
            artist: selected.primary_artists || selected.singers,
            album: selected.album,
            duration: parseInt(selected.duration, 10) || 240,
            artwork: (selected.image || '').replace('150x150', '500x500'),
            streamUrl: streamUrl
          };
          fullSongCache.set(cacheKey, songObj);
          return songObj;
        }
      }
    } catch (err) {}
  }

  // Tier 2: SoundCloud Global Stream (~400ms, covers 300M+ international, indie, phonk, remixes)
  for (const v of variants) {
    try {
      const scUrl = `https://api-v2.soundcloud.com/search/tracks?q=${encodeURIComponent(v)}&client_id=${activeScClientId}&limit=5`;
      const scRes = await fetch(scUrl, {
        headers: { 'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36' },
        signal: AbortSignal.timeout(2500)
      });
      if (scRes.ok) {
        const scData = await scRes.json();
        const items = scData.collection || [];
        for (const track of items) {
          const trans = track.media?.transcodings?.find(tc => tc.format?.protocol === 'progressive') || track.media?.transcodings?.[0];
          if (trans) {
            const streamRes = await fetch(`${trans.url}?client_id=${activeScClientId}`, { signal: AbortSignal.timeout(2500) });
            if (streamRes.ok) {
              const streamJson = await streamRes.json();
              if (streamJson.url) {
                const songObj = {
                  success: true,
                  provider: 'soundcloud-full',
                  title: track.title,
                  artist: track.user?.username || 'Artist',
                  album: track.publisher_metadata?.album_title || 'Single',
                  duration: Math.round((track.duration || 210000) / 1000),
                  artwork: (track.artwork_url || track.user?.avatar_url || '').replace('large.jpg', 't500x500.jpg'),
                  streamUrl: streamJson.url
                };
                fullSongCache.set(cacheKey, songObj);
                return songObj;
              }
            }
          }
        }
      }
    } catch (err) {}
  }

  // Tier 3: Apple Music Storefront
  const country = isCjk ? 'jp' : 'in';
  try {
    const itunesUrl = `https://itunes.apple.com/search?term=${encodeURIComponent(query)}&country=${country}&media=music&entity=song&limit=5`;
    const res = await fetch(itunesUrl, { signal: AbortSignal.timeout(3000) });
    if (res.ok) {
      const data = await res.json();
      if (data.results && data.results.length > 0) {
        const item = data.results.find(r => r.previewUrl) || data.results[0];
        if (item && item.previewUrl) {
          const songObj = {
            success: true,
            provider: `itunes-aac-${country}`,
            title: item.trackName,
            artist: item.artistName,
            album: item.collectionName || 'Single',
            duration: Math.round((item.trackTimeMillis || 210000) / 1000),
            artwork: (item.artworkUrl100 || '').replace('100x100bb', '600x600bb'),
            streamUrl: item.previewUrl
          };
          fullSongCache.set(cacheKey, songObj);
          return songObj;
        }
      }
    }
  } catch (err) {}

  return null;
}

const lyricsCache = new Map();

function parseLrc(lrcText) {
  if (!lrcText) return [];
  const lines = lrcText.split('\n');
  const result = [];
  const timeRegex = /\[(\d{2}):(\d{2})\.(\d{2,3})\](.*)/;

  for (const line of lines) {
    const match = line.match(timeRegex);
    if (match) {
      const mins = parseInt(match[1], 10);
      const secs = parseInt(match[2], 10);
      const ms = parseInt(match[3].padEnd(3, '0').slice(0, 3), 10);
      const time = mins * 60 + secs + ms / 1000;
      const text = match[4].trim();
      if (text) {
        result.push({ time, text });
      }
    }
  }
  return result;
}

async function resolveLyrics(track, artist) {
  const cleanTrack = (track || '').replace(/\s*\(From "[^"]*"\)/gi, '').replace(/\s*\(feat\.[^)]*\)/gi, '').replace(/\s*\[[^\]]*\]/gi, '').trim();
  const cleanArtist = (artist || '').split(',')[0].trim();
  const cacheKey = `${cleanTrack.toLowerCase()}___${cleanArtist.toLowerCase()}`;

  if (lyricsCache.has(cacheKey)) {
    return lyricsCache.get(cacheKey);
  }

  // 1. Try LRCLIB direct get
  try {
    const url = `https://lrclib.net/api/get?track_name=${encodeURIComponent(cleanTrack)}&artist_name=${encodeURIComponent(cleanArtist)}`;
    const res = await fetch(url, { headers: { 'User-Agent': 'Mozilla/5.0 SONiC Music/2.0' }, signal: AbortSignal.timeout(3000) });
    if (res.ok) {
      const data = await res.json();
      if (data.syncedLyrics || data.plainLyrics) {
        const payload = {
          success: true,
          synced: !!data.syncedLyrics,
          lines: parseLrc(data.syncedLyrics),
          plain: data.plainLyrics || ''
        };
        lyricsCache.set(cacheKey, payload);
        return payload;
      }
    }
  } catch (err) {}

  // 2. Try LRCLIB search
  try {
    const searchUrl = `https://lrclib.net/api/search?q=${encodeURIComponent(cleanTrack + ' ' + cleanArtist)}`;
    const sRes = await fetch(searchUrl, { headers: { 'User-Agent': 'Mozilla/5.0 SONiC Music/2.0' }, signal: AbortSignal.timeout(3000) });
    if (sRes.ok) {
      const list = await sRes.json();
      if (Array.isArray(list) && list.length > 0) {
        const item = list[0];
        if (item.syncedLyrics || item.plainLyrics) {
          const payload = {
            success: true,
            synced: !!item.syncedLyrics,
            lines: parseLrc(item.syncedLyrics),
            plain: item.plainLyrics || ''
          };
          lyricsCache.set(cacheKey, payload);
          return payload;
        }
      }
    }
  } catch (err) {}

  return {
    success: false,
    synced: false,
    message: 'Lyrics not found'
  };
}

async function searchMultiCatalog(query) {
  const cleanQ = (query || '').trim();
  const variants = extractQueryVariants(cleanQ);
  const isCjk = hasCJK(cleanQ);

  const tracks = [];
  const artists = [];
  const albums = [];
  const seenTracks = new Set();
  const seenArtists = new Set();
  const seenAlbums = new Set();

  function addTrack(t) {
    const key = `${t.title} - ${t.artist}`.toLowerCase().replace(/[^a-z0-9]/g, '');
    if (!seenTracks.has(key)) {
      seenTracks.add(key);
      tracks.push(t);
    }
  }

  // 1. Search JioSaavn first (~300ms, high fidelity 320k)
  for (const v of variants) {
    if (hasCJK(v) && !/[a-zA-Z0-9]/.test(v)) continue;
    try {
      const saavnUrl = 'https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=15&p=1&q=' + encodeURIComponent(v);
      const res = await fetch(saavnUrl, {
        headers: { 'User-Agent': 'Mozilla/5.0' },
        signal: AbortSignal.timeout(2500)
      });
      const data = await res.json();
      (data.results || []).forEach(s => {
        addTrack({
          id: `saavn_${s.id}`,
          title: s.song,
          artist: s.primary_artists || s.singers || 'Unknown Artist',
          album: s.album || 'Single',
          artwork: (s.image || '').replace('150x150', '500x500'),
          previewUrl: '',
          durationMs: (parseInt(s.duration, 10) || 215) * 1000,
          releaseDate: s.year || ''
        });
      });
    } catch (err) {}
    if (tracks.length >= 10) break;
  }

  // 2. Search SoundCloud (~400ms, covers 300M+ international, indie, phonk, remixes)
  try {
    const scUrl = `https://api-v2.soundcloud.com/search/tracks?q=${encodeURIComponent(cleanQ)}&client_id=${activeScClientId}&limit=12`;
    const res = await fetch(scUrl, {
      headers: { 'User-Agent': 'Mozilla/5.0' },
      signal: AbortSignal.timeout(2500)
    });
    if (res.ok) {
      const data = await res.json();
      (data.collection || []).forEach(t => {
        addTrack({
          id: `sc_${t.id}`,
          title: t.title,
          artist: t.user?.username || 'Artist',
          album: 'Single',
          artwork: (t.artwork_url || t.user?.avatar_url || '').replace('large.jpg', 't500x500.jpg'),
          previewUrl: '',
          durationMs: t.duration || 215000,
          releaseDate: t.created_at || ''
        });
      });
    }
  } catch (err) {}

  // 3. Fallback to iTunes if fewer than 5 tracks found
  if (tracks.length < 5) {
    const country = isCjk ? 'jp' : 'in';
    try {
      const itunesUrl = `https://itunes.apple.com/search?term=${encodeURIComponent(cleanQ)}&country=${country}&media=music&limit=15`;
      const res = await fetch(itunesUrl, { signal: AbortSignal.timeout(2500) });
      if (res.ok) {
        const data = await res.json();
        (data.results || []).forEach(item => {
          if (item.kind === 'song' || item.trackName) {
            addTrack({
              id: String(item.trackId || Math.floor(Math.random() * 1000000)),
              title: item.trackName,
              artist: item.artistName || 'Unknown Artist',
              album: item.collectionName || 'Single',
              artwork: (item.artworkUrl100 || '').replace('100x100bb', '600x600bb'),
              previewUrl: item.previewUrl || '',
              durationMs: item.trackTimeMillis || 215000,
              releaseDate: item.releaseDate || ''
            });
          }
        });
      }
    } catch (err) {}
  }

  return { tracks: tracks.slice(0, 35), artists: artists.slice(0, 8), albums: albums.slice(0, 8) };
}

const server = http.createServer(async (req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  const parsedUrl = new URL(req.url, `http://${req.headers.host || 'localhost'}`);
  const pathname = parsedUrl.pathname;

  if (pathname === '/api/lyrics') {
    const track = parsedUrl.searchParams.get('track');
    const artist = parsedUrl.searchParams.get('artist') || '';
    if (!track) {
      res.writeHead(400, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ success: false, error: 'Track name is required' }));
      return;
    }

    const lyricsData = await resolveLyrics(track, artist);
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify(lyricsData));
    return;
  }

  if (pathname === '/api/search') {
    const q = parsedUrl.searchParams.get('q');
    if (!q) {
      res.writeHead(400, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ success: false, error: 'Query parameter q is required' }));
      return;
    }

    const searchResults = await searchMultiCatalog(q);
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ success: true, ...searchResults }));
    return;
  }

  // SONiC Connect Multi-Device API Endpoints
  if (pathname === '/api/connect/devices') {
    cleanStaleDevices();
    const list = Array.from(connectedDevices.values());
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
      success: true,
      localIp: getLocalNetworkIp(),
      localUrl: `http://${getLocalNetworkIp()}:${PORT}`,
      devices: list
    }));
    return;
  }

  if (pathname === '/api/connect/register' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        const data = JSON.parse(body || '{}');
        if (data.id) {
          connectedDevices.set(data.id, {
            id: data.id,
            name: data.name || 'Web Browser',
            type: data.type || 'desktop',
            currentTrack: data.currentTrack || null,
            isPlaying: !!data.isPlaying,
            lastSeen: Date.now()
          });
        }
        cleanStaleDevices();
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ success: true, count: connectedDevices.size }));
      } catch (e) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ success: false, error: 'Invalid JSON' }));
      }
    });
    return;
  }

  if (pathname === '/api/connect/command' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        const data = JSON.parse(body || '{}');
        if (data.targetId) {
          pendingCommands.set(data.targetId, {
            action: data.action,
            track: data.track,
            position: data.position,
            from: data.from || 'Remote',
            time: Date.now()
          });
        }
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ success: true }));
      } catch (e) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ success: false, error: 'Invalid JSON' }));
      }
    });
    return;
  }

  if (pathname === '/api/connect/poll') {
    const deviceId = parsedUrl.searchParams.get('deviceId');
    const cmd = deviceId ? pendingCommands.get(deviceId) : null;
    if (cmd) {
      pendingCommands.delete(deviceId);
    }
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ success: true, command: cmd || null }));
    return;
  }

  if (pathname === '/api/fullsong') {
    const q = parsedUrl.searchParams.get('q');
    if (!q) {
      res.writeHead(400, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ success: false, error: 'Query parameter q is required' }));
      return;
    }

    const song = await resolveFullSong(q);
    if (song) {
      res.writeHead(200, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify(song));
    } else {
      res.writeHead(404, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ success: false, error: 'Full song not found' }));
    }
    return;
  }

  let filePath = path.join(PUBLIC_DIR, pathname === '/' ? 'index.html' : pathname);

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('404 Not Found');
      return;
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';

    res.writeHead(200, {
      'Content-Type': contentType,
      'Cache-Control': 'no-cache'
    });

    const stream = fs.createReadStream(filePath);
    stream.pipe(res);
  });
});

server.listen(PORT, () => {
  console.log(`SONiC Server running on http://localhost:${PORT}`);
});
