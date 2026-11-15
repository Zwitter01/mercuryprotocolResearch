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

module.exports = async (req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    return res.status(200).end();
  }

  const track = req.query.track;
  const artist = req.query.artist || '';

  if (!track) {
    return res.status(400).json({ success: false, error: 'Track name is required' });
  }

  const cleanTrack = (track || '').replace(/\s*\(From "[^"]*"\)/gi, '').replace(/\s*\(feat\.[^)]*\)/gi, '').replace(/\s*\[[^\]]*\]/gi, '').trim();
  const cleanArtist = (artist || '').split(',')[0].trim();

  try {
    const url = `https://lrclib.net/api/get?track_name=${encodeURIComponent(cleanTrack)}&artist_name=${encodeURIComponent(cleanArtist)}`;
    const lRes = await fetch(url, { headers: { 'User-Agent': 'Mozilla/5.0 SONiC Music/2.0' } });
    if (lRes.ok) {
      const data = await lRes.json();
      if (data.syncedLyrics || data.plainLyrics) {
        return res.status(200).json({
          success: true,
          synced: !!data.syncedLyrics,
          lines: parseLrc(data.syncedLyrics),
          plain: data.plainLyrics || ''
        });
      }
    }

    const searchUrl = `https://lrclib.net/api/search?q=${encodeURIComponent(cleanTrack + ' ' + cleanArtist)}`;
    const sRes = await fetch(searchUrl, { headers: { 'User-Agent': 'Mozilla/5.0 SONiC Music/2.0' } });
    if (sRes.ok) {
      const list = await sRes.json();
      if (Array.isArray(list) && list.length > 0) {
        const item = list[0];
        if (item.syncedLyrics || item.plainLyrics) {
          return res.status(200).json({
            success: true,
            synced: !!item.syncedLyrics,
            lines: parseLrc(item.syncedLyrics),
            plain: item.plainLyrics || ''
          });
        }
      }
    }

    return res.status(200).json({
      success: false,
      synced: false,
      message: 'Lyrics not found'
    });
  } catch (err) {
    return res.status(500).json({ success: false, error: err.message });
  }
};
