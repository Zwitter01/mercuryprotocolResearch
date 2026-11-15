# SONiC Native Android App (Option 2: Kotlin + Media3 ExoPlayer)

## Step-by-Step Development Plan

### Milestone 1: Project Foundation & Background Audio Engine (Completed)
- Native Android Gradle setup (Android SDK 35/36, OpenJDK 21 LTS)
- Jetpack Compose Material 3 UI foundation with edge-to-edge support
- "Aurora Noir" color palette and typography ported from web player
- Media3 ExoPlayer Background Playback Service (`SonicMediaService`)
- Android MediaSession and Lock Screen media notification controls
- Audio Focus management (pauses during phone calls, resumes automatically)
- Persistent Bottom Mini-Player Dock
- Home Screen layout with Mood Filter Pills (Auto-Mood, Romantic, Punjabi, Party, Sad, Chill, Retro)

### Milestone 2: Live Music Streaming & Search API Integration (Completed)
- Retrofit API client to fetch songs from backend (`server.cjs`) and search endpoints
- Real-time search screen with debounced search queries (350ms debounce)
- Infinite scrolling and lazy-loaded playlists
- 3-tab navigation (Home, Search, Your Library)

### Milestone 3: Full-Screen Player, Queue & Lyrics (Completed)
- Full-screen expanding player with large album art, scrubber, and playback controls
- Synchronized time-stamped lyrics viewer (matching web player lyrics with auto-scroll)
- Up Next / Playback Queue manager with interactive track selection
- Shuffle and Repeat modes

### Milestone 4: Offline Vault & Local Downloads (Completed)
- Android native SQLite database (`SonicDatabaseHelper`) for offline song storage
- Background streaming download engine (`SonicDownloadManager`) saving audio and high-res cover art
- Real-time percentage progress tracking (0..100%)
- Offline Library screen (`DownloadedTracksScreen`) with instant 1-tap playback and file size summary
- Zero-internet playback mode

### Milestone 5: SONiC Connect (Multi-Device Remote Control & Web Sync) (Completed)
- Real-time heartbeat registration (`/api/connect/register`) with active device status
- Background command polling (`/api/connect/poll`) for incoming remote commands
- Wi-Fi network discovery (`/api/connect/devices`) to list active PCs, laptops, and web browsers
- 1-tap seamless playback transfer between Android Phone and PC Web Player (`/api/connect/command`)
- Remote control panel (Play, Pause, Next, Previous) to control PC playback directly from phone
- Aurora Noir styled Connect dialog with server endpoint configurator

---

## How to Open in Android Studio
1. Open Android Studio.
2. Select "Open" and browse to: `C:\Users\kvish\Desktop\Sonic\android` (or OneDrive mirrored path).
3. Android Studio will automatically sync Gradle and download dependencies.
4. Connect your Android phone via USB (with USB Debugging ON) or launch an emulator.
5. Click the green "Run" button to install and run SONiC on your phone.
