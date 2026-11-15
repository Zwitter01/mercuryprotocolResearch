# SONiC Web Music Player - Vercel Deployment

This is the production-ready build for deploying the SONiC Web Music Player to Vercel.

## How to Deploy to Vercel

### Method 1: Vercel Dashboard (Drag & Drop or Git)
1. Push this `dist` folder to a GitHub repository (e.g. `sonic-web-player`).
2. Go to [https://vercel.com/new](https://vercel.com/new).
3. Import the repository.
4. Leave all build settings default (Framework: Other, Root Directory: `./`).
5. Click **Deploy**.

### Method 2: Vercel CLI (Direct 1-command deployment)
Run the following in PowerShell from this folder:
```powershell
npx vercel
```
Follow the prompts (login, confirm project name), and your site will be live within 30 seconds at `https://sonic-music-player.vercel.app`!

## Features Included
- Exact Aurora Noir styling and responsive mobile design
- Full Catalog (Popular Artists, Albums, Radio, Charts, Hits)
- Full 320kbps audio streaming via serverless `/api/fullsong` endpoint
- Real-time karaoke synchronized lyrics via serverless `/api/lyrics` endpoint
- Global CDN caching and zero-config deployment
