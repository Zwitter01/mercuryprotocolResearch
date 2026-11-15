const crypto = require('crypto');

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

module.exports = async (req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    return res.status(200).end();
  }

  const query = req.query.q;
  if (!query) {
    return res.status(400).json({ success: false, error: 'Query parameter q is required' });
  }

  try {
    const url = 'https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=5&p=1&q=' + encodeURIComponent(query);
    const apiRes = await fetch(url, {
      headers: {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
      }
    });
    const data = await apiRes.json();
    if (data.results && data.results.length > 0) {
      const s = data.results[0];
      const streamUrl = decryptSaavnMediaUrl(s.encrypted_media_url);
      if (streamUrl) {
        return res.status(200).json({
          success: true,
          title: s.song,
          artist: s.primary_artists || s.singers,
          album: s.album,
          duration: parseInt(s.duration, 10) || 240,
          artwork: (s.image || '').replace('150x150', '500x500'),
          streamUrl: streamUrl
        });
      }
    }
    return res.status(404).json({ success: false, error: 'Full song not found' });
  } catch (err) {
    return res.status(500).json({ success: false, error: err.message });
  }
};
