const http = require('http');
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');

const PORT = 3300;
const PUBLIC_DIR = __dirname;

// MIME types dictionary
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
  '.mp3': 'audio/mpeg'
};

// Saavn DES3 Decryption (Equivalent to DES-ECB with key '38346591')
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

// In-memory cache for resolved full-length songs
const fullSongCache = new Map();

async function resolveFullSong(query) {
  const cacheKey = query.toLowerCase().trim();
  if (fullSongCache.has(cacheKey)) {
    return fullSongCache.get(cacheKey);
  }

  try {
    const url = 'https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=5&p=1&q=' + encodeURIComponent(query);
    const res = await fetch(url, {
      headers: {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
      }
    });
    const data = await res.json();
    if (data.results && data.results.length > 0) {
      const s = data.results[0];
      const streamUrl = decryptSaavnMediaUrl(s.encrypted_media_url);
      if (streamUrl) {
        const fullSong = {
          success: true,
          title: s.song,
          artist: s.primary_artists || s.singers,
          album: s.album,
          duration: parseInt(s.duration, 10) || 240,
          artwork: (s.image || '').replace('150x150', '500x500'),
          streamUrl: streamUrl
        };
        fullSongCache.set(cacheKey, fullSong);
        return fullSong;
      }
    }
  } catch (err) {
    console.warn('Saavn resolve error for query', query, err.message);
  }

  return null;
}

const server = http.createServer(async (req, res) => {
  // CORS Headers
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

  // 1. API: Full Length Song Resolver
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

  // 2. Static File Serving
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
