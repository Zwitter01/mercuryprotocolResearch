/**
 * SONiC Real Music Catalog & Shelves
 * Exact matching data for all sections in user screenshots:
 * - Popular Artists (Image 5)
 * - Popular Albums and Singles (Image 2)
 * - Popular Radio (Image 3)
 * - Charts (Image 6)
 * - Today's Biggest Hits (Image 7)
 * - Based on your recent listening (Image 4)
 * - Sad Songs (Image 8)
 * - Throwback (Image 9)
 * - Top Picks in New Music (Image 10)
 * - Party (Image 11)
 * - Sing-Along (Image 12)
 * - Library Items
 */

const SONIC_CATALOG = {
  // 1. Popular Artists (Image 5)
  popularArtists: [
    {
      id: 'art_pritam',
      name: 'Pritam',
      type: 'artist',
      monthlyListeners: '45,210,840',
      verified: true,
      query: 'Pritam',
      image: 'assets/artists/pritam.jpg',
      bio: 'Hitmaker composer behind two decades of iconic Bollywood chartbusters and melodious anthems.'
    },
    {
      id: 'art_ar_rahman',
      name: 'A.R. Rahman',
      type: 'artist',
      monthlyListeners: '37,842,500',
      verified: true,
      query: 'A.R. Rahman',
      image: 'assets/artists/ar_rahman.jpg',
      bio: 'Academy and Grammy Award-winning maestro who transformed Indian film music.'
    },
    {
      id: 'art_arijit',
      name: 'Arijit Singh',
      type: 'artist',
      monthlyListeners: '49,120,450',
      verified: true,
      query: 'Arijit Singh',
      image: 'assets/artists/arijit_singh.jpg',
      bio: 'The undisputed voice of Bollywood romance, melody, and acoustic soul.'
    },
    {
      id: 'art_sachin_jigar',
      name: 'Sachin-Jigar',
      type: 'artist',
      monthlyListeners: '28,490,110',
      verified: true,
      query: 'Sachin-Jigar',
      image: 'assets/artists/sachin_jigar.jpg',
      bio: 'Dynamic music producer duo blending Gujarati folk with contemporary pop and Bollywood anthems.'
    },
    {
      id: 'art_vishal_shekhar',
      name: 'Vishal-Shekhar',
      type: 'artist',
      monthlyListeners: '26,380,200',
      verified: true,
      query: 'Vishal-Shekhar',
      image: 'assets/artists/vishal_shekhar.jpg',
      bio: 'Pioneering composer duo behind blockbuster party hits, high-energy rock and romantic melodies.'
    },
    {
      id: 'art_atif',
      name: 'Atif Aslam',
      type: 'artist',
      monthlyListeners: '29,820,310',
      verified: true,
      query: 'Atif Aslam',
      image: 'assets/artists/atif_aslam.jpg',
      bio: 'Iconic vocalist renowned for vocal belting, soulful romantic ballads, and rock anthems.'
    },
    {
      id: 'art_anirudh',
      name: 'Anirudh Ravichander',
      type: 'artist',
      monthlyListeners: '27,450,900',
      verified: true,
      query: 'Anirudh Ravichander',
      image: 'assets/artists/anirudh_ravichander.jpg',
      bio: 'Rockstar music director redefining South Indian and pan-Indian commercial cinema soundtracks.'
    },
    {
      id: 'art_udit',
      name: 'Udit Narayan',
      type: 'artist',
      monthlyListeners: '22,410,900',
      verified: true,
      query: 'Udit Narayan',
      image: 'assets/artists/udit_narayan.jpg',
      bio: 'Legendary playback singer with four National Film Awards and timeless 90s/2000s classics.'
    },
    {
      id: 'art_honey',
      name: 'Yo Yo Honey Singh',
      type: 'artist',
      monthlyListeners: '32,150,600',
      verified: true,
      query: 'Yo Yo Honey Singh',
      image: 'assets/artists/honey_singh.jpg',
      bio: 'Pioneer of modern Desi Hip-Hop, Punjabi rap, and blockbuster Bollywood music.'
    },
    {
      id: 'art_shankar_ehsaan_loy',
      name: 'Shankar-Ehsaan-Loy',
      type: 'artist',
      monthlyListeners: '19,840,700',
      verified: true,
      query: 'Shankar-Ehsaan-Loy',
      image: 'assets/artists/shankar_ehsaan_loy.jpg',
      bio: 'The Amar Akbar Anthony of contemporary Indian music, fusing classical ragas with rock and jazz.'
    },
    {
      id: 'art_shreya_ghoshal',
      name: 'Shreya Ghoshal',
      type: 'artist',
      monthlyListeners: '35,920,800',
      verified: true,
      query: 'Shreya Ghoshal',
      image: 'assets/artists/shreya_ghoshal.jpg',
      bio: 'Melody queen of Indian cinema with five National Film Awards and timeless vocal brilliance.'
    },
    {
      id: 'art_jasleen_royal',
      name: 'Jasleen Royal',
      type: 'artist',
      monthlyListeners: '21,340,500',
      verified: true,
      query: 'Jasleen Royal',
      image: 'assets/artists/jasleen_royal.jpg',
      bio: 'Singer-songwriter behind heartfelt acoustic hits like Din Shagna Da, Ranjha, and Heeriye.'
    },
    {
      id: 'art_amit_trivedi',
      name: 'Amit Trivedi',
      type: 'artist',
      monthlyListeners: '18,920,400',
      verified: true,
      query: 'Amit Trivedi',
      image: 'assets/artists/amit_trivedi.jpg',
      bio: 'Innovator of soulful, folk-infused modern soundtracks like Dev.D, Queen, and Kedarnath.'
    },
    {
      id: 'art_sachet_tandon',
      name: 'Sachet Tandon',
      type: 'artist',
      monthlyListeners: '24,180,600',
      verified: true,
      query: 'Sachet Tandon',
      image: 'assets/artists/sachet_tandon.jpg',
      bio: 'Powerhouse vocalist behind Bekhayali, Maiyya Mainu, and romantic superhits.'
    },
    {
      id: 'art_alka_yagnik',
      name: 'Alka Yagnik',
      type: 'artist',
      monthlyListeners: '27,700,300',
      verified: true,
      query: 'Alka Yagnik',
      image: 'assets/artists/alka_yagnik.jpg',
      bio: 'Guinness record-holding legendary voice with over a thousand cinematic evergreen classics.'
    },
    {
      id: 'art_himesh_reshammiya',
      name: 'Himesh Reshammiya',
      type: 'artist',
      monthlyListeners: '23,450,100',
      verified: true,
      query: 'Himesh Reshammiya',
      image: 'assets/artists/himesh_reshammiya.jpg',
      bio: 'Sufi-rock icon and composer who swept the subcontinent with unforgettable viral anthems.'
    },
    {
      id: 'art_anuv_jain',
      name: 'Anuv Jain',
      type: 'artist',
      monthlyListeners: '14,620,800',
      verified: true,
      query: 'Anuv Jain',
      image: 'assets/artists/anuv_jain.jpg',
      bio: 'Indie-pop singer-songwriter telling intimate coming-of-age love stories with acoustic guitar and ukulele.'
    },
    {
      id: 'art_shubh',
      name: 'Shubh',
      type: 'artist',
      monthlyListeners: '25,810,400',
      verified: true,
      query: 'Shubh',
      image: 'assets/artists/shubh.jpg',
      bio: 'Global Punjabi hip-hop sensation behind chart-topping bangers like No Love, Still Rollin, and Cheques.'
    },
    {
      id: 'art_badshah',
      name: 'Badshah',
      type: 'artist',
      monthlyListeners: '31,450,200',
      verified: true,
      query: 'Badshah',
      image: 'assets/artists/badshah.jpg',
      bio: 'Global Desi hip-hop titan dominating clubs and festivals worldwide with high-octane rap anthems.'
    },
    {
      id: 'art_sachet_parampara',
      name: 'Sachet-Parampara',
      type: 'artist',
      monthlyListeners: '20,780,900',
      verified: true,
      query: 'Sachet-Parampara',
      image: 'assets/artists/sachet_parampara.jpg',
      bio: 'Dynamic composer-singer duo crafting soul-stirring romantic and devotional epics.'
    }
  ],

  // 2. Popular Albums and Singles (Image 2)
  popularAlbums: [
    {
      id: 'alb_aashiqui2',
      title: 'Aashiqui 2',
      subtitle: 'Mithoon, Ankit Tiwari, Jeet Gannguli',
      query: 'Aashiqui 2',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg',
      type: 'album',
      year: '2013'
    },
    {
      id: 'alb_sanam',
      title: 'Sanam Teri Kasam (Original Motion...)',
      subtitle: 'Himesh Reshammiya, Sameer Anjaan, Subrat...',
      query: 'Sanam Teri Kasam',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/44/65/2a/44652a17-ed54-7224-ca3e-281a2f704412/196871079150.jpg/600x600bb.jpg',
      type: 'album',
      year: '2016'
    },
    {
      id: 'alb_raanjhan',
      title: 'Raanjhan (From "Do Patti")',
      subtitle: 'Sachet-Parampara, Parampara Tandon,...',
      query: 'Do Patti Raanjhan',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/99/a9/ca/99a9caf7-5d9f-5890-3fdf-e3ff8c860e79/8903431018915_cover.jpg/600x600bb.jpg',
      type: 'single',
      year: '2024'
    },
    {
      id: 'alb_findingher',
      title: 'Finding Her',
      subtitle: 'Kushagra, Bharath, Saaheal',
      query: 'Finding Her Kushagra',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/0e/4f/58/0e4f5828-64f8-26d5-ebde-661b6fee89fe/5034644353067.jpg/600x600bb.jpg',
      type: 'single',
      year: '2024'
    },
    {
      id: 'alb_arijit_love',
      title: 'Ultimate Love Songs - Arijit Singh',
      subtitle: 'Arijit Singh',
      query: 'Arijit Singh Hawayein',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg',
      type: 'album',
      year: '2024'
    },
    {
      id: 'alb_kabir_singh',
      title: 'Kabir Singh',
      subtitle: 'Sachet-Parampara, Vishal Mishra, Mithoon',
      query: 'Kabir Singh Bekhayali',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/91/4e/c6/914ec63c-741c-30b1-3963-7eb6bbdd5fa9/8902894357326_cover.jpg/600x600bb.jpg',
      type: 'album',
      year: '2019'
    },
    {
      id: 'alb_yjhd',
      title: 'Yeh Jawaani Hai Deewani',
      subtitle: 'Pritam, Amitabh Bhattacharya',
      query: 'Yeh Jawaani Hai Deewani',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/4a/1d/a5/4a1da567-27b9-b883-93ea-00f6b3e944b1/887845347206.jpg/600x600bb.jpg',
      type: 'album',
      year: '2013'
    },
    {
      id: 'alb_animal',
      title: 'Animal (Original Motion Picture)',
      subtitle: 'JAM8, Vishal Mishra, Harshavardhan',
      query: 'Animal Arjan Vailly',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/28/7f/ee/287fee5d-c6a6-f28e-8a07-8ff7c569fdf5/8903431969392_cover.jpg/600x600bb.jpg',
      type: 'album',
      year: '2023'
    },
    {
      id: 'alb_rockstar',
      title: 'Rockstar',
      subtitle: 'A.R. Rahman, Mohit Chauhan, Irshad Kamil',
      query: 'Rockstar Nadaan Parindey',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/48/7e/19/487e19d7-feea-5fa7-2f78-297feeb1b0ca/886443217032.jpg/600x600bb.jpg',
      type: 'album',
      year: '2011'
    }
  ],

  // 3. Popular Radio (Image 3)
  popularRadio: [
    {
      id: 'rad_arijit',
      title: 'Arijit Singh',
      subtitle: 'With Pritam, Sachet Tandon, Jeet Ganng...',
      color: '#f0c242',
      textColor: '#000000',
      query: 'Arijit Singh Kesariya',
      avatars: [
        'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/200x200bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/300x300bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/2e/0b/c0/2e0bc070-112f-a827-6ad8-6bc64f7caaff/840214460180.png/200x200bb.jpg'
      ]
    },
    {
      id: 'rad_sidhu',
      title: 'Sidhu Moose Wala',
      subtitle: 'With Shubh, Sharry Mann, Prem Dhillon...',
      color: '#9d9fe8',
      textColor: '#000000',
      query: 'Sidhu Moose Wala',
      avatars: [
        'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/f1/57/9d/f1579d5f-4e4f-1235-e97c-fa725602c839/810105711448_cover.jpg/600x600bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/37/ca/50/37ca5028-a2dc-975b-6366-6427c2b81761/5064089945930_cover.jpg/300x300bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/af/11/f9/af11f978-7b08-8025-eb57-25682c8d9acb/5054197842245.jpg/600x600bb.jpg'
      ]
    },
    {
      id: 'rad_diljit',
      title: 'Diljit Dosanjh',
      subtitle: 'With Harrdy Sandhu, AP Dhillon, Shubh an...',
      color: '#85d8c6',
      textColor: '#000000',
      query: 'Diljit Dosanjh Lover',
      avatars: [
        'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/af/11/f9/af11f978-7b08-8025-eb57-25682c8d9acb/5054197842245.jpg/600x600bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/24/2a/a4/242aa4a9-808f-419f-289c-e7e6a1517c08/4593106.jpg/300x300bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/d2/89/ac/d289ac98-749e-3822-6b6e-b06aa4815715/859740651597_cover.jpg/600x600bb.jpg'
      ]
    },
    {
      id: 'rad_kishore',
      title: 'Kishore Kumar',
      subtitle: 'With Mukesh, Hemant Kumar, Kalyanji-...',
      color: '#f25b50',
      textColor: '#000000',
      query: 'Kishore Kumar Pal Pal Dil Ke Paas',
      avatars: [
        'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b4/62/03/b46203d4-ffce-982f-ba72-410240feb42c/191773212155.jpg/200x200bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/0c/69/70/0c697018-bfe3-2d2f-92fb-125e6e796027/192562482773.jpg/600x600bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/46/10/ae/4610ae2a-c419-bb77-9128-e2eac5291b05/191773204372.jpg/600x600bb.jpg'
      ]
    },
    {
      id: 'rad_arrahman',
      title: 'A.R. Rahman',
      subtitle: 'With Anirudh Ravichander, Harris Jayaraj...',
      color: '#e8c82a',
      textColor: '#000000',
      query: 'A.R. Rahman Kun Faya Kun',
      avatars: [
        'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/200x200bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/a8/92/ca/a892ca65-89ae-7ce3-ce08-e5aef83cfd11/00602527009230.rgb.jpg/600x600bb.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/38/44/21/384421ca-69a0-83f2-0a34-88653d45db97/8901854001385.jpg/200x200bb.jpg'
      ]
    },
    {
      id: 'rad_atif',
      title: 'Atif Aslam',
      subtitle: 'With Rahat Fateh Ali Khan, Mustafa Zahid, Pritam...',
      color: '#5b8bf7',
      textColor: '#ffffff',
      query: 'Atif Aslam Woh Lamhe',
      avatars: [
        'assets/artists/atif_aslam.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/200x200bb.jpg',
        'assets/artists/pritam.jpg'
      ]
    },
    {
      id: 'rad_honey',
      title: 'Yo Yo Honey Singh',
      subtitle: 'With Badshah, Guru Randhawa, Raftaar, Diljit...',
      color: '#e69500',
      textColor: '#ffffff',
      query: 'Yo Yo Honey Singh Dope Shope',
      avatars: [
        'assets/artists/honey_singh.jpg',
        'assets/artists/badshah.jpg',
        'assets/artists/shubh.jpg'
      ]
    },
    {
      id: 'rad_shreya',
      title: 'Shreya Ghoshal',
      subtitle: 'With Sunidhi Chauhan, Sonu Nigam, Alka Yagnik...',
      color: '#b056e6',
      textColor: '#ffffff',
      query: 'Shreya Ghoshal Deewani Mastani',
      avatars: [
        'assets/artists/shreya_ghoshal.jpg',
        'assets/artists/alka_yagnik.jpg',
        'assets/artists/arijit_singh.jpg'
      ]
    },
    {
      id: 'rad_kk',
      title: 'KK (Krishnakumar Kunnath)',
      subtitle: 'With Mohit Chauhan, Lucky Ali, Javed Ali...',
      color: '#38a169',
      textColor: '#ffffff',
      query: 'KK Zara Sa',
      avatars: [
        'assets/artists/pritam.jpg',
        'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/4a/1d/a5/4a1da567-27b9-b883-93ea-00f6b3e944b1/887845347206.jpg/600x600bb.jpg',
        'assets/artists/ar_rahman.jpg'
      ]
    }
  ],

  // 4. Charts (Image 6)
  charts: [
    {
      id: 'cht_hindi',
      title: 'HOT HITS HINDI',
      subtitle: 'Hottest Hindi music that India is listening...',
      query: 'Ghungroo War',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'cht_telugu',
      title: 'HOT HITS TELUGU',
      subtitle: 'Tune in to the Hottest Tracks of Tollywood!...',
      query: 'Urike Urike',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/dd/39/14/dd3914e5-a2f3-b355-51f3-9a1f0e3ca246/8903431853592_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'cht_tamil',
      title: 'HOT HITS TAMIL',
      subtitle: 'Tune into the Hottest tracks of Kollywood!...',
      query: 'Top Tucker Rashmika',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/99/4c/e2/994ce25e-0fe1-1287-972d-7ab5f09e55e1/8902894360616_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'cht_top50_india',
      title: 'Top 50 - India',
      subtitle: 'Your daily update of the most played tracks right now - India.',
      query: 'Top 50 India Bollywood',
      image: 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'600\' height=\'600\' viewBox=\'0 0 600 600\'%3E%3Cdefs%3E%3ClinearGradient id=\'top50ind\' x1=\'0%25\' y1=\'0%25\' x2=\'0%25\' y2=\'100%25\'%3E%3Cstop offset=\'0%25\' stop-color=\'%23b8be35\'/%3E%3Cstop offset=\'45%25\' stop-color=\'%232f7a4d\'/%3E%3Cstop offset=\'100%25\' stop-color=\'%23144528\'/%3E%3C/linearGradient%3E%3C/defs%3E%3Crect width=\'600\' height=\'600\' rx=\'20\' fill=\'url(%23top50ind)\'/%3E%3Ccircle cx=\'65\' cy=\'65\' r=\'28\' fill=\'%23ffffff\' fill-opacity=\'0.3\'/%3E%3Cpath fill=\'%23ffffff\' fill-opacity=\'0.9\' d=\'M55 52c6 0 11 2 15 5l-4 5c-3-2-7-3-11-3-8 0-14 5-14 13 0 7 6 12 14 12 4 0 8-1 11-3l4 5c-4 3-9 5-15 5-12 0-21-8-21-19s9-20 21-20z\'/%3E%3Ctext x=\'300\' y=\'270\' text-anchor=\'middle\' font-family=\'system-ui,-apple-system,sans-serif\' font-weight=\'900\' font-size=\'88\' fill=\'%23ffffff\' letter-spacing=\'-3\'%3ETop 50%3C/text%3E%3Cline x1=\'150\' y1=\'310\' x2=\'450\' y2=\'310\' stroke=\'%23ffffff\' stroke-width=\'4\' stroke-opacity=\'0.35\'/%3E%3Ctext x=\'300\' y=\'375\' text-anchor=\'middle\' font-family=\'system-ui,-apple-system,sans-serif\' font-weight=\'800\' font-size=\'36\' fill=\'%23ffffff\' fill-opacity=\'0.9\' letter-spacing=\'10\'%3EINDIA%3C/text%3E%3C/svg%3E'
    },
    {
      id: 'cht_top50_global',
      title: 'Top 50 - Global',
      subtitle: 'Your daily update of the most played tracks right now - Global.',
      query: 'Top 50 Global Pop',
      image: 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'600\' height=\'600\' viewBox=\'0 0 600 600\'%3E%3Cdefs%3E%3ClinearGradient id=\'top50glb\' x1=\'0%25\' y1=\'0%25\' x2=\'0%25\' y2=\'100%25\'%3E%3Cstop offset=\'0%25\' stop-color=\'%231e3264\'/%3E%3Cstop offset=\'100%25\' stop-color=\'%230e1a38\'/%3E%3C/linearGradient%3E%3C/defs%3E%3Crect width=\'600\' height=\'600\' rx=\'20\' fill=\'url(%23top50glb)\'/%3E%3Ccircle cx=\'65\' cy=\'65\' r=\'28\' fill=\'%23ffffff\' fill-opacity=\'0.3\'/%3E%3Ctext x=\'300\' y=\'270\' text-anchor=\'middle\' font-family=\'system-ui,-apple-system,sans-serif\' font-weight=\'900\' font-size=\'88\' fill=\'%23ffffff\' letter-spacing=\'-3\'%3ETop 50%3C/text%3E%3Cline x1=\'150\' y1=\'310\' x2=\'450\' y2=\'310\' stroke=\'%23ffffff\' stroke-width=\'4\' stroke-opacity=\'0.35\'/%3E%3Ctext x=\'300\' y=\'375\' text-anchor=\'middle\' font-family=\'system-ui,-apple-system,sans-serif\' font-weight=\'800\' font-size=\'36\' fill=\'%23ffffff\' fill-opacity=\'0.9\' letter-spacing=\'10\'%3EGLOBAL%3C/text%3E%3C/svg%3E'
    },
    {
      id: 'cht_latest_tamil',
      title: 'Latest Tamil தமிழ்',
      subtitle: 'New Music from Kollywood. Cover:...',
      query: 'Hai Re Anirudh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'cht_punjabi',
      title: 'HOT HITS PUNJABI',
      subtitle: 'Catch the hottest Punjabi tracks. Cover:...',
      query: 'Brown Munde',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/47/47/ac/4747ac85-1658-64ae-bc82-220a4d6213d5/859747478890_cover.jpg/600x600bb.jpg'
    }
  ],

  // 5. Today's biggest hits (Image 7)
  biggestHits: [
    {
      id: 'hit_punjabi',
      title: 'HOT HITS PUNJABI',
      subtitle: 'Catch the hottest Punjabi tracks. Cover:...',
      query: 'Lover Diljit Dosanjh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_valentine',
      title: 'Trending Valentine\'s Hits',
      subtitle: 'Songs India is falling in love with right no...',
      query: 'Kesariya Brahmastra',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_desiindie',
      title: 'DESI INDIE',
      subtitle: 'Home to the best of South Asian Indie...',
      query: 'Faasle Aditya A',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/d5/29/29/d5292970-e6e4-1199-baed-22c8c9f60988/cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_viral_punjabi',
      title: 'Trending Now Punjabi',
      subtitle: 'All that\'s viral, stream now! Cover: Arjan...',
      query: 'Kaali Hoodie Sidhu',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/db/2c/84/db2c84f2-f42f-af93-2709-6158995f9f72/810105712582_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_dance_bollywood',
      title: 'Bollywood Dance Music',
      subtitle: 'Party-ready, the biggest Bollywood...',
      query: 'Desi Kalakaar Honey Singh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_mega_bollywood',
      title: 'Mega Bollywood Mashup 2026',
      subtitle: 'The ultimate dancefloor and party anthems...',
      query: 'Jhoome Jo Pathaan',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/0c/b1/13/0cb11358-1f19-b684-2194-e0bca3809ee5/8902894360678_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_soulful_romance',
      title: 'Soulful Bollywood Romance',
      subtitle: 'Heart-touching acoustic & unplugged hits...',
      query: 'Tum Hi Ho Aashiqui 2',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_punjabi_2026',
      title: 'Punjabi 100% Top Hits',
      subtitle: 'Top charts from Sidhu, Shubh, Diljit, Karan...',
      query: 'Cheques Shubh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'hit_desi_pop',
      title: 'Desi Pop Wave',
      subtitle: 'Catch the fresh new sound of Indian pop...',
      query: 'Heeriye Jasleen Royal',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/af/11/f9/af11f978-7b08-8025-eb57-25682c8d9acb/5054197842245.jpg/600x600bb.jpg'
    }
  ],

  // 6. Based on your recent listening (Image 4)
  recentListening: [
    {
      id: 'rec_madeinindia',
      title: 'Made in India',
      subtitle: 'Made in India, streamed everywhe...',
      query: 'Made in India Alisha',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/f6/70/84/f6708434-0123-ff36-0ac3-7401e8cf0f94/8902894360807_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_sundowner',
      title: 'Bollywood Sundowner',
      subtitle: 'Kickstart your sundowner party wit...',
      query: 'Besharam Rang Pathaan',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_safarmix',
      title: 'Safar Mix',
      subtitle: 'A perfect travel mix for your journey.',
      query: 'Safarnama Lucky Ali',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/2e/0b/c0/2e0bc070-112f-a827-6ad8-6bc64f7caaff/840214460180.png/600x600bb.jpg'
    },
    {
      id: 'rec_desipop',
      title: 'DESI POP',
      subtitle: 'Home to the Desi Pop Bops. For more desi...',
      query: 'Lover Diljit Dosanjh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_desihits',
      title: 'DESI HITS',
      subtitle: 'Best of South Asian Music. Cover: Hasa...',
      query: 'Brown Munde AP Dhillon',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/47/47/ac/4747ac85-1658-64ae-bc82-220a4d6213d5/859747478890_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_night_vibes',
      title: 'Late Night Hindi Lo-Fi',
      subtitle: 'Chill lo-fi flips of your favorite songs...',
      query: 'Kasoor Prateek Kuhad',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/d5/29/29/d5292970-e6e4-1199-baed-22c8c9f60988/cover.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_roadtrip',
      title: 'Bollywood Highway Drive',
      subtitle: 'Roll the windows down and turn the volume up...',
      query: 'Matargashti Tamasha',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/4a/1d/a5/4a1da567-27b9-b883-93ea-00f6b3e944b1/887845347206.jpg/600x600bb.jpg'
    },
    {
      id: 'rec_coffee',
      title: 'Coffee & Acoustic Hindi',
      subtitle: 'Calm morning acoustic vibes with tea...',
      query: 'Baarishein Anuv Jain',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/0e/4f/58/0e4f5828-64f8-26d5-ebde-661b6fee89fe/5034644353067.jpg/600x600bb.jpg'
    }
  ],

  // 7. Sad songs (Image 8)
  sadSongs: [
    {
      id: 'sad_90s',
      title: '90s Sad Bollywood',
      subtitle: 'Soothe your broken heart with these...',
      query: 'Tujhe Dekha Toh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_allthefeels',
      title: 'All The Feels',
      subtitle: 'All the vibes. All the feels.',
      query: 'Channa Mereya',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_tanhayee',
      title: 'Tanhayee...',
      subtitle: 'It\'s ok to not be okay. Nostalgic songs that...',
      query: 'Tanhayee Dil Chahta Hai',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/2b/df/62/2bdf6213-3d11-5f01-cc5e-ddfcc15923b4/8902894114844_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_starter',
      title: 'sad girl starter pack',
      subtitle: 'Sapphic songs that defined your music...',
      query: 'Cardigan Taylor Swift',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/38/44/21/384421ca-69a0-83f2-0a34-88653d45db97/8901854001385.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_heartbroken',
      title: 'Heartbroken Hindi',
      subtitle: 'Music to sit through the tears...',
      query: 'Tujhe Kitna Chahne Lage',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/f6/70/84/f6708434-0123-ff36-0ac3-7401e8cf0f94/8902894360807_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_raina_beeti',
      title: 'Raina Beeti Jaye',
      subtitle: 'Quiet nocturnal tears and slow reflections...',
      query: 'Agar Tum Saath Ho',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_breakup_blues',
      title: 'Breakup Blues Hindi',
      subtitle: 'When moving on feels impossible...',
      query: 'Kabira Yeh Jawaani',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/4a/1d/a5/4a1da567-27b9-b883-93ea-00f6b3e944b1/887845347206.jpg/600x600bb.jpg'
    },
    {
      id: 'sad_yaad_piya_ki',
      title: 'Yaad Piya Ki Aaye',
      subtitle: 'Echoes of memories that linger on...',
      query: 'Jag Ghoomeya Sultan',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    }
  ],

  // 8. Throwback (Image 9)
  throwback: [
    {
      id: 'tb_movie',
      title: '[my life is a movie]',
      subtitle: 'every main character needs their...',
      query: 'Iktara Wake Up Sid',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_hindi10s',
      title: 'All Out Hindi 10s',
      subtitle: 'Relive the biggest tracks of the decade...',
      query: 'Tum Hi Ho Aashiqui',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_20s',
      title: 'All Out 20s',
      subtitle: 'The biggest songs of the 2020s. Cover:...',
      query: 'Apna Bana Le Bhediya',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/2e/0b/c0/2e0bc070-112f-a827-6ad8-6bc64f7caaff/840214460180.png/600x600bb.jpg'
    },
    {
      id: 'tb_onehit',
      title: 'Just Try to Forget These Songs',
      subtitle: 'The one-hit wonders that will stay stuck in...',
      query: 'Kolaveri Di Anirudh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_90s_love',
      title: '90s Romantic Bollywood',
      subtitle: 'Kumar Sanu, Alka Yagnik, Udit Narayan...',
      query: 'Tujhe Dekha Toh Yeh Jaana Sanam',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_indipop',
      title: 'Indipop Nostalgia',
      subtitle: 'Lucky Ali, Euphoria, Silk Route, Alisha...',
      query: 'O Sanam Lucky Ali',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/f6/70/84/f6708434-0123-ff36-0ac3-7401e8cf0f94/8902894360807_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_y2k_hits',
      title: 'Y2K Bollywood Bangers',
      subtitle: 'Kabhi Khushi Kabhie Gham, Kal Ho Naa Ho...',
      query: 'Bole Chudiyan',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'tb_sufi_nights',
      title: 'Timeless Sufi Classics',
      subtitle: 'Nusrat Fateh Ali Khan, Rahat, Kailash Kher...',
      query: 'Tere Bin Nahi Lagda',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    }
  ],

  // 9. Top Picks in New Music (Image 10)
  newMusic: [
    {
      id: 'new_tauba_tauba',
      title: 'Tauba Tauba (From "Bad Newz")',
      subtitle: 'Karan Aujla • Latest Chartbuster',
      query: 'Tauba Tauba Karan Aujla',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/dd/39/14/dd3914e5-a2f3-b355-51f3-9a1f0e3ca246/8903431853592_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_chal_kudiye',
      title: 'Chal Kudiye (From "Jigra")',
      subtitle: 'Diljit Dosanjh, Alia Bhatt • Fresh Drop',
      query: 'Chal Kudiye Diljit',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/af/11/f9/af11f978-7b08-8025-eb57-25682c8d9acb/5054197842245.jpg/600x600bb.jpg'
    },
    {
      id: 'new_illuminate',
      title: 'Illuminati (From "Aavesham")',
      subtitle: 'Sushin Shyam, Dabzee • Viral Sensation',
      query: 'Illuminati Aavesham',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_aaj_ki_raat',
      title: 'Aaj Ki Raat (From "Stree 2")',
      subtitle: 'Sachin-Jigar, Madhubanti Bagchi, Divya Kumar',
      query: 'Aaj Ki Raat Stree 2',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_millionaire',
      title: 'Millionaire (From "Glory")',
      subtitle: 'Yo Yo Honey Singh • Global Viral',
      query: 'Millionaire Honey Singh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_big_dawgs',
      title: 'Big Dawgs',
      subtitle: 'Hanumankind, Kalmi • Worldwide Hit',
      query: 'Big Dawgs Hanumankind',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_aaja',
      title: 'Aaja Meri Jaani',
      subtitle: 'electronic kili, Arcado • Fresh Drop',
      query: 'Aaja Meri Jaani',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/bc/b1/dd/bcb1dd65-bfbc-8666-c59b-8c007e664236/823375454786_Cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_wild',
      title: 'WILD',
      subtitle: 'KATSEYE • International Release',
      query: 'KATSEYE Touch',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/dd/39/14/dd3914e5-a2f3-b355-51f3-9a1f0e3ca246/8903431853592_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_penne',
      title: 'Penne Ponne',
      subtitle: 'Akhilesh Ramachandran, Simm',
      query: 'Penne Ponne',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/0e/4f/58/0e4f5828-64f8-26d5-ebde-661b6fee89fe/5034644353067.jpg/600x600bb.jpg'
    },
    {
      id: 'new_dilka',
      title: 'Dil Ka Maamla',
      subtitle: 'Neha Karim Ullah • Romantic Single',
      query: 'Dil Ka Maamla',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/99/a9/ca/99a9caf7-5d9f-5890-3fdf-e3ff8c860e79/8903431018915_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'new_tuhitu',
      title: 'Tu Hi Tu',
      subtitle: 'Ruhani • Acoustic Melody',
      query: 'Tu Hi Tu Salman',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/44/65/2a/44652a17-ed54-7224-ca3e-281a2f704412/196871079150.jpg/600x600bb.jpg'
    }
  ],

  // 10. Party (Image 11)
  party: [
    {
      id: 'pty_newyear',
      title: 'New Year Party',
      subtitle: 'Dance your way to 2026 with these...',
      query: 'Abhi Toh Party Shuru Hui Hai',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_girls',
      title: 'Girls\' Night',
      subtitle: 'It\'s all about the girls tonight.',
      query: 'Kudi Nu Nachne De',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_ipop',
      title: 'I-Pop Party',
      subtitle: 'Beats per minute just got hectic! Rock the...',
      query: 'Desi Kalakaar Honey',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music71/v4/81/0b/a4/810ba47e-952c-8818-ad3c-9899b4928cd4/8902894354943_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_00s',
      title: '00\'s Dance Bollywood',
      subtitle: 'Groove it to the biggest bangers of...',
      query: 'Mauja Hi Mauja',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_wedding',
      title: 'Punjabi Wedding',
      subtitle: 'The big fat playlist for your grand weddi...',
      query: 'Sauda Khara Khara',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_club_hindi',
      title: 'Bollywood Club Anthem',
      subtitle: 'Non-stop bass heavy remix energy...',
      query: 'Kar Gayi Chull',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/4a/1d/a5/4a1da567-27b9-b883-93ea-00f6b3e944b1/887845347206.jpg/600x600bb.jpg'
    },
    {
      id: 'pty_garba_beats',
      title: 'Dhol & Garba Nights',
      subtitle: 'High energy festive dance beats...',
      query: 'Chogada Loveyatri',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/2e/0b/c0/2e0bc070-112f-a827-6ad8-6bc64f7caaff/840214460180.png/600x600bb.jpg'
    },
    {
      id: 'pty_house_party',
      title: 'House Party Bollywood',
      subtitle: 'The essential house party soundtrack...',
      query: 'Kala Chashma Baar Baar Dekho',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/f6/70/84/f6708434-0123-ff36-0ac3-7401e8cf0f94/8902894360807_cover.jpg/600x600bb.jpg'
    }
  ],

  // 11. Sing Along (Image 12)
  singAlong: [
    {
      id: 'sng_tiptip',
      title: 'Tip Tip Barsa',
      subtitle: 'Soak into nostalgia with these fun songs...',
      query: 'Tip Tip Barsa Paani',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/6f/3c/65/6f3c65a3-dee7-9977-574f-8d5e604774aa/cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_car',
      title: 'Songs To Sing In The Car',
      subtitle: 'Sing along and enjoy the drive',
      query: 'Illuminati Aavesham',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_disney',
      title: 'Disney Sing-Alongs',
      subtitle: 'Sing your heart out with the Disney Sing...',
      query: 'Let It Go Frozen',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/2e/0b/c0/2e0bc070-112f-a827-6ad8-6bc64f7caaff/840214460180.png/600x600bb.jpg'
    },
    {
      id: 'sng_screampop',
      title: 'pop songs we can all scream',
      subtitle: 'the ones you can\'t sing loud enough',
      query: 'Viva La Vida Coldplay',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_girlpower',
      title: 'Girl Power Karaoke',
      subtitle: 'You had to make your own sunshine, and...',
      query: 'Param Sundari Mimi',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/99/4c/e2/994ce25e-0fe1-1287-972d-7ab5f09e55e1/8902894360616_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_bollywood_choir',
      title: 'Full Volume Hindi Anthems',
      subtitle: 'Songs everyone in the room knows by heart...',
      query: 'Gallan Goodiyaan Dil Dhadakne Do',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_acoustic_unplugged',
      title: 'Acoustic Sing-Alongs',
      subtitle: 'Warm guitars and unforgettable choruses...',
      query: 'Pani Da Rang Ayushmann',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/38/44/21/384421ca-69a0-83f2-0a34-88653d45db97/8901854001385.jpg/600x600bb.jpg'
    },
    {
      id: 'sng_90s_karaoke',
      title: 'Golden 90s Karaoke Gold',
      subtitle: 'Nostalgic duets you cant stop singing...',
      query: 'Chura Ke Dil Mera',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg'
    }
  ],

  // 12. Library Sidebar Items
  libraryItems: [
    {
      id: 'lib_liked',
      title: 'Liked Songs',
      type: 'playlist',
      pinned: true,
      subtitle: 'Playlist • Pinned',
      isLikedCollection: true,
      image: 'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 64 64"><defs><linearGradient id="g" x1="0" y1="0" x2="1" y2="1"><stop offset="0%" stop-color="%23450af5"/><stop offset="100%" stop-color="%238e8ee5"/></linearGradient></defs><rect width="64" height="64" rx="6" fill="url(%23g)"/><path fill="white" d="M32 46.5l-2.4-2.2C20.8 36.8 15 31.5 15 25c0-5.3 4.2-9.5 9.5-9.5 3 0 5.9 1.4 7.5 3.6 1.6-2.2 4.5-3.6 7.5-3.6 5.3 0 9.5 4.2 9.5 9.5 0 6.5-5.8 11.8-14.6 19.3L32 46.5z"/></svg>'
    },
    {
      id: 'lib_aashiqui2',
      title: 'Aashiqui 2',
      type: 'album',
      subtitle: 'Album • Mithoon, Ankit Tiwari',
      query: 'Aashiqui 2',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'lib_arijit_rad',
      title: 'Arijit Singh Radio',
      type: 'radio',
      subtitle: 'Artist Radio • SONiC',
      query: 'Arijit Singh Kesariya',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg'
    },
    {
      id: 'lib_honey',
      title: 'Yo Yo Honey Singh',
      type: 'artist',
      subtitle: 'Artist',
      query: 'Yo Yo Honey Singh',
      image: 'assets/artists/honey_singh.jpg'
    },
    {
      id: 'lib_hothits',
      title: 'HOT HITS HINDI',
      type: 'playlist',
      subtitle: 'Playlist • SONiC',
      query: 'Ghungroo War',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'lib_diljit',
      title: 'Diljit Dosanjh',
      type: 'artist',
      subtitle: 'Artist',
      query: 'Diljit Dosanjh',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg'
    },
    {
      id: 'lib_sanam',
      title: 'Sanam Teri Kasam',
      type: 'album',
      subtitle: 'Album • Himesh Reshammiya',
      query: 'Sanam Teri Kasam',
      image: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/44/65/2a/44652a17-ed54-7224-ca3e-281a2f704412/196871079150.jpg/600x600bb.jpg'
    }
  ]
};

const SONIC_OFFLINE_SONGS = [
  // 1. Bollywood Blockbusters (15)
  {
    id: 'off_01',
    title: 'Kesariya',
    artist: 'Pritam, Arijit Singh',
    album: 'Brahmastra',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 268,
    durationMs: 268000,
    artwork: 'https://c.saavncdn.com/871/Brahmastra-Original-Motion-Picture-Soundtrack-Hindi-2022-20221006155213-500x500.jpg',
    query: 'Kesariya Brahmastra',
    offlineReady: true
  },
  {
    id: 'off_02',
    title: 'Tum Hi Ho',
    artist: 'Mithoon, Arijit Singh',
    album: 'Aashiqui 2',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 262,
    durationMs: 262000,
    artwork: 'https://c.saavncdn.com/430/Aashiqui-2-Hindi-2013-500x500.jpg',
    query: 'Tum Hi Ho Aashiqui 2',
    offlineReady: true
  },
  {
    id: 'off_03',
    title: 'Raataan Lambiyan',
    artist: 'Tanishk Bagchi, Jubin Nautiyal, Asees Kaur',
    album: 'Shershaah',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 230,
    durationMs: 230000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/61/65/ae/6165aee9-8bb9-0bd4-02b0-5d0f1e6257a3/886449510238.jpg/600x600bb.jpg',
    query: 'Raataan Lambiyan Shershaah',
    offlineReady: true
  },
  {
    id: 'off_04',
    title: 'Apna Bana Le',
    artist: 'Sachin-Jigar, Arijit Singh',
    album: 'Bhediya',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 261,
    durationMs: 261000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg',
    query: 'Apna Bana Le Bhediya',
    offlineReady: true
  },
  {
    id: 'off_05',
    title: 'Channa Mereya',
    artist: 'Pritam, Arijit Singh',
    album: 'Ae Dil Hai Mushkil',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 289,
    durationMs: 289000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg',
    query: 'Channa Mereya Ae Dil Hai Mushkil',
    offlineReady: true
  },
  {
    id: 'off_06',
    title: 'Raanjhan',
    artist: 'Sachet-Parampara, Parampara Tandon',
    album: 'Do Patti',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 240,
    durationMs: 240000,
    artwork: 'https://c.saavncdn.com/436/Do-Patti-Hindi-2024-20250509181115-500x500.jpg',
    query: 'Do Patti Raanjhan',
    offlineReady: true
  },
  {
    id: 'off_07',
    title: 'Sanam Teri Kasam',
    artist: 'Himesh Reshammiya, Ankit Tiwari',
    album: 'Sanam Teri Kasam',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 314,
    durationMs: 314000,
    artwork: 'https://c.saavncdn.com/150/Sanam-Teri-Kasam-Hindi-2025-20260404111304-500x500.jpg',
    query: 'Sanam Teri Kasam',
    offlineReady: true
  },
  {
    id: 'off_08',
    title: 'Tere Vaaste',
    artist: 'Sachin-Jigar, Varun Jain, Shadab Faridi',
    album: 'Zara Hatke Zara Bachke',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 189,
    durationMs: 189000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg',
    query: 'Tere Vaaste Zara Hatke Zara Bachke',
    offlineReady: true
  },
  {
    id: 'off_09',
    title: 'Kalank Title Track',
    artist: 'Pritam, Arijit Singh',
    album: 'Kalank',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 311,
    durationMs: 311000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/4e/39/49/4e3949f3-d59d-4295-4bb2-036269f9a88f/8718857678083.png/600x600bb.jpg',
    query: 'Kalank Title Track Arijit',
    offlineReady: true
  },
  {
    id: 'off_10',
    title: 'Hawayein',
    artist: 'Pritam, Arijit Singh',
    album: 'Jab Harry Met Sejal',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 290,
    durationMs: 290000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/9f/13/ca/9f13ca3b-e533-03e0-f19a-f0aaa774581d/196589311191.jpg/600x600bb.jpg',
    query: 'Hawayein Arijit Singh',
    offlineReady: true
  },
  {
    id: 'off_11',
    title: 'Agar Tum Saath Ho',
    artist: 'A.R. Rahman, Alka Yagnik, Arijit Singh',
    album: 'Tamasha',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 341,
    durationMs: 341000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/75/76/90/75769099-e111-feae-1eb1-fe068b39cf5c/8903431198303_cover.jpg/600x600bb.jpg',
    query: 'Agar Tum Saath Ho Tamasha',
    offlineReady: true
  },
  {
    id: 'off_12',
    title: 'Shayad',
    artist: 'Pritam, Arijit Singh',
    album: 'Love Aaj Kal',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 247,
    durationMs: 247000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/bb/23/ee/bb23eeed-0c35-4f1d-2b11-485622777ae4/8902894353007_cover.jpg/600x600bb.jpg',
    query: 'Shayad Love Aaj Kal',
    offlineReady: true
  },
  {
    id: 'off_13',
    title: 'Dil Diyan Gallan',
    artist: 'Vishal-Shekhar, Atif Aslam',
    album: 'Tiger Zinda Hai',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 260,
    durationMs: 260000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/38/44/21/384421ca-69a0-83f2-0a34-88653d45db97/8901854001385.jpg/600x600bb.jpg',
    query: 'Dil Diyan Gallan Atif Aslam',
    offlineReady: true
  },
  {
    id: 'off_14',
    title: 'Tujh Mein Rab Dikhta Hai',
    artist: 'Salim-Sulaiman, Roop Kumar Rathod',
    album: 'Rab Ne Bana Di Jodi',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 283,
    durationMs: 283000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg',
    query: 'Tujh Mein Rab Dikhta Hai',
    offlineReady: true
  },
  {
    id: 'off_15',
    title: 'Pehla Nasha',
    artist: 'Jatin-Lalit, Udit Narayan, Sadhana Sargam',
    album: 'Jo Jeeta Wohi Sikandar',
    genre: 'bollywood',
    genreLabel: 'Bollywood Hits',
    durationSec: 291,
    durationMs: 291000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/96/c9/3a/96c93aa7-1872-8297-493a-2fc72d540af0/191773221072.jpg/600x600bb.jpg',
    query: 'Pehla Nasha Udit Narayan',
    offlineReady: true
  },

  // 2. Punjabi & Desi Beats (12)
  {
    id: 'off_16',
    title: '295',
    artist: 'Sidhu Moose Wala',
    album: 'Moosetape',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 270,
    durationMs: 270000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/37/ca/50/37ca5028-a2dc-975b-6366-6427c2b81761/5064089945930_cover.jpg/600x600bb.jpg',
    query: '295 Sidhu Moose Wala',
    offlineReady: true
  },
  {
    id: 'off_17',
    title: 'The Last Ride',
    artist: 'Sidhu Moose Wala, Wazir Patar',
    album: 'The Last Ride',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 261,
    durationMs: 261000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/db/2c/84/db2c84f2-f42f-af93-2709-6158995f9f72/810105712582_cover.jpg/600x600bb.jpg',
    query: 'The Last Ride Sidhu Moose Wala',
    offlineReady: true
  },
  {
    id: 'off_18',
    title: 'Lover',
    artist: 'Diljit Dosanjh, Intense',
    album: 'MoonChild Era',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 184,
    durationMs: 184000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/89/e4/8a89e445-d2c6-f8ac-a828-27818b0c1afe/859749638209_cover.jpg/600x600bb.jpg',
    query: 'Diljit Dosanjh Lover',
    offlineReady: true
  },
  {
    id: 'off_19',
    title: 'Born to Shine',
    artist: 'Diljit Dosanjh, Desi Crew',
    album: 'G.O.A.T.',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 213,
    durationMs: 213000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/24/2a/a4/242aa4a9-808f-419f-289c-e7e6a1517c08/4593106.jpg/600x600bb.jpg',
    query: 'Born to Shine Diljit Dosanjh',
    offlineReady: true
  },
  {
    id: 'off_20',
    title: 'Brown Munde',
    artist: 'AP Dhillon, Gurinder Gill, Shinda Kahlon',
    album: 'Not by Chance',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 267,
    durationMs: 267000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/47/47/ac/4747ac85-1658-64ae-bc82-220a4d6213d5/859747478890_cover.jpg/600x600bb.jpg',
    query: 'Brown Munde AP Dhillon',
    offlineReady: true
  },
  {
    id: 'off_21',
    title: 'Excuses',
    artist: 'AP Dhillon, Gurinder Gill',
    album: 'Hidden Gems',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 176,
    durationMs: 176000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/47/47/ac/4747ac85-1658-64ae-bc82-220a4d6213d5/859747478890_cover.jpg/600x600bb.jpg',
    query: 'Excuses AP Dhillon',
    offlineReady: true
  },
  {
    id: 'off_22',
    title: 'Softly',
    artist: 'Karan Aujla, Ikky',
    album: 'Making Memories',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 155,
    durationMs: 155000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/4f/d6/06/4fd60662-385f-cfc4-e73c-2832ebd58d2d/5021732829061.jpg/600x600bb.jpg',
    query: 'Softly Karan Aujla',
    offlineReady: true
  },
  {
    id: 'off_23',
    title: 'Winning Speech',
    artist: 'Karan Aujla, Mxrci',
    album: 'Four Me',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 212,
    durationMs: 212000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/4f/d6/06/4fd60662-385f-cfc4-e73c-2832ebd58d2d/5021732829061.jpg/600x600bb.jpg',
    query: 'Winning Speech Karan Aujla',
    offlineReady: true
  },
  {
    id: 'off_24',
    title: 'Elevate',
    artist: 'Karan Aujla, Ikky',
    album: 'Street Dreams',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 198,
    durationMs: 198000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/4f/d6/06/4fd60662-385f-cfc4-e73c-2832ebd58d2d/5021732829061.jpg/600x600bb.jpg',
    query: 'Elevate Karan Aujla',
    offlineReady: true
  },
  {
    id: 'off_25',
    title: 'Cheques',
    artist: 'Shubh',
    album: 'Still Rollin',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 183,
    durationMs: 183000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/e9/bd/93/e9bd9316-75a6-bc15-aebe-c737037bedf0/196925634489.jpg/600x600bb.jpg',
    query: 'Cheques Shubh',
    offlineReady: true
  },
  {
    id: 'off_26',
    title: 'Baller',
    artist: 'Shubh, Ikky',
    album: 'Baller',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 148,
    durationMs: 148000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/e9/bd/93/e9bd9316-75a6-bc15-aebe-c737037bedf0/196925634489.jpg/600x600bb.jpg',
    query: 'Baller Shubh',
    offlineReady: true
  },
  {
    id: 'off_27',
    title: 'Dope Shope',
    artist: 'Yo Yo Honey Singh, Deep Money',
    album: 'International Villager',
    genre: 'punjabi',
    genreLabel: 'Punjabi Hits',
    durationSec: 189,
    durationMs: 189000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg',
    query: 'Dope Shope Honey Singh',
    offlineReady: true
  },

  // 3. Lo-Fi & Chill Beats (10)
  {
    id: 'off_28',
    title: 'Midnight Rain Lo-Fi Beat',
    artist: 'SONiC Chill',
    album: 'Rainy Night In Delhi',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 210,
    durationMs: 210000,
    artwork: 'https://images.unsplash.com/photo-1518495973542-4542c06a5843?w=600&auto=format&fit=crop&q=80',
    query: 'Midnight Rain Lofi',
    offlineReady: true
  },
  {
    id: 'off_29',
    title: 'Delhi Nights Acoustic Guitar',
    artist: 'SONiC Acoustic',
    album: 'Unplugged Vibes',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 195,
    durationMs: 195000,
    artwork: 'https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=600&auto=format&fit=crop&q=80',
    query: 'Acoustic Guitar Melodies',
    offlineReady: true
  },
  {
    id: 'off_30',
    title: 'Safar Chill Hop',
    artist: 'SONiC Chillhop',
    album: 'Highway Drive',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 225,
    durationMs: 225000,
    artwork: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=600&auto=format&fit=crop&q=80',
    query: 'Chillhop Beats',
    offlineReady: true
  },
  {
    id: 'off_31',
    title: 'Chai & Monsoon Rain Lo-Fi',
    artist: 'SONiC Rainy Moods',
    album: 'Monsoon Memories',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 240,
    durationMs: 240000,
    artwork: 'https://images.unsplash.com/photo-1515694346937-94d85e41e6f0?w=600&auto=format&fit=crop&q=80',
    query: 'Monsoon Rain Lofi',
    offlineReady: true
  },
  {
    id: 'off_32',
    title: 'Late Night Drive Ambient',
    artist: 'SONiC Ambient',
    album: 'Neon Lights',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 232,
    durationMs: 232000,
    artwork: 'https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600&auto=format&fit=crop&q=80',
    query: 'Late Night Ambient',
    offlineReady: true
  },
  {
    id: 'off_33',
    title: 'Mountain Breeze Flute & Piano',
    artist: 'SONiC Peace',
    album: 'Himalayan Serenity',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 250,
    durationMs: 250000,
    artwork: 'https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600&auto=format&fit=crop&q=80',
    query: 'Flute Piano Meditation',
    offlineReady: true
  },
  {
    id: 'off_34',
    title: 'Nostalgia Tape Cassette',
    artist: 'SONiC Vintage',
    album: 'Tape Hiss Stories',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 204,
    durationMs: 204000,
    artwork: 'https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80',
    query: 'Vintage Tape Lofi',
    offlineReady: true
  },
  {
    id: 'off_35',
    title: 'Study Session Coffeehouse Lo-Fi',
    artist: 'SONiC Study Club',
    album: 'Focus & Relax',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 218,
    durationMs: 218000,
    artwork: 'https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=600&auto=format&fit=crop&q=80',
    query: 'Coffeehouse Lofi Study',
    offlineReady: true
  },
  {
    id: 'off_36',
    title: 'Starry Sky Melodic Piano',
    artist: 'SONiC Instrumental',
    album: 'Night Sky Serenade',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 220,
    durationMs: 220000,
    artwork: 'https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=600&auto=format&fit=crop&q=80',
    query: 'Relaxing Piano Solo',
    offlineReady: true
  },
  {
    id: 'off_37',
    title: 'Deep Sleep Ambient Waves',
    artist: 'SONiC Sleep',
    album: 'Slumber Waves',
    genre: 'lofi',
    genreLabel: 'Lo-Fi & Chill',
    durationSec: 260,
    durationMs: 260000,
    artwork: 'https://images.unsplash.com/photo-1518241353330-0f7941c2d9b5?w=600&auto=format&fit=crop&q=80',
    query: 'Slumber Ambient Waves',
    offlineReady: true
  },

  // 4. Party & Dance Anthems (8)
  {
    id: 'off_38',
    title: 'Kar Gayi Chull',
    artist: 'Badshah, Fazilpuria, Neha Kakkar',
    album: 'Kapoor & Sons',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 187,
    durationMs: 187000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg',
    query: 'Kar Gayi Chull Badshah',
    offlineReady: true
  },
  {
    id: 'off_39',
    title: 'Kala Chashma',
    artist: 'Amar Arshi, Badshah, Neha Kakkar',
    album: 'Baar Baar Dekho',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 187,
    durationMs: 187000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/dd/39/14/dd3914e5-a2f3-b355-51f3-9a1f0e3ca246/8903431853592_cover.jpg/600x600bb.jpg',
    query: 'Kala Chashma Baar Baar Dekho',
    offlineReady: true
  },
  {
    id: 'off_40',
    title: 'Ghungroo',
    artist: 'Vishal-Shekhar, Arijit Singh, Shilpa Rao',
    album: 'War',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 302,
    durationMs: 302000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/f4/0b/88/f40b88d5-75cb-27b8-6b00-bba98f0f2fd0/849486006911_cover.jpg/600x600bb.jpg',
    query: 'Ghungroo War Arijit Singh',
    offlineReady: true
  },
  {
    id: 'off_41',
    title: 'Brown Rang',
    artist: 'Yo Yo Honey Singh',
    album: 'International Villager',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 179,
    durationMs: 179000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg',
    query: 'Brown Rang Honey Singh',
    offlineReady: true
  },
  {
    id: 'off_42',
    title: 'Blue Eyes',
    artist: 'Yo Yo Honey Singh',
    album: 'Blue Eyes',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 220,
    durationMs: 220000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/b3/b5/d9/b3b5d986-7f6d-a860-b8aa-769e1eef1a92/8902894356299_cover.jpg/600x600bb.jpg',
    query: 'Blue Eyes Honey Singh',
    offlineReady: true
  },
  {
    id: 'off_43',
    title: 'Hookah Bar',
    artist: 'Himesh Reshammiya, Vineet Singh',
    album: 'Khiladi 786',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 254,
    durationMs: 254000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/44/65/2a/44652a17-ed54-7224-ca3e-281a2f704412/196871079150.jpg/600x600bb.jpg',
    query: 'Hookah Bar Himesh Reshammiya',
    offlineReady: true
  },
  {
    id: 'off_44',
    title: 'Aankh Marey',
    artist: 'Neha Kakkar, Mika Singh, Kumar Sanu',
    album: 'Simmba',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 213,
    durationMs: 213000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/99/4c/e2/994ce25e-0fe1-1287-972d-7ab5f09e55e1/8902894360616_cover.jpg/600x600bb.jpg',
    query: 'Aankh Marey Simmba',
    offlineReady: true
  },
  {
    id: 'off_45',
    title: 'Naatu Naatu',
    artist: 'M. M. Keeravani, Rahul Sipligunj',
    album: 'RRR',
    genre: 'party',
    genreLabel: 'Party & Dance',
    durationSec: 216,
    durationMs: 216000,
    artwork: 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/dd/39/14/dd3914e5-a2f3-b355-51f3-9a1f0e3ca246/8903431853592_cover.jpg/600x600bb.jpg',
    query: 'Naatu Naatu RRR',
    offlineReady: true
  },

  // 5. Global & International Hits (10)
  {
    id: 'off_46',
    title: 'Believer',
    artist: 'Imagine Dragons',
    album: 'Evolve',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 204,
    durationMs: 204000,
    artwork: 'https://c.saavncdn.com/248/Evolve-English-2018-20260605220036-500x500.jpg',
    query: 'Imagine Dragons Believer',
    offlineReady: true
  },
  {
    id: 'off_47',
    title: 'Bones',
    artist: 'Imagine Dragons',
    album: 'Mercury - Acts 1 & 2',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 165,
    durationMs: 165000,
    artwork: 'https://c.saavncdn.com/248/Evolve-English-2018-20260605220036-500x500.jpg',
    query: 'Imagine Dragons Bones',
    offlineReady: true
  },
  {
    id: 'off_48',
    title: 'Shape of You',
    artist: 'Ed Sheeran',
    album: '÷ (Divide)',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 233,
    durationMs: 233000,
    artwork: 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=600&auto=format&fit=crop&q=80',
    query: 'Shape of You Ed Sheeran',
    offlineReady: true
  },
  {
    id: 'off_49',
    title: 'Perfect',
    artist: 'Ed Sheeran',
    album: '÷ (Divide)',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 263,
    durationMs: 263000,
    artwork: 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=600&auto=format&fit=crop&q=80',
    query: 'Perfect Ed Sheeran',
    offlineReady: true
  },
  {
    id: 'off_50',
    title: 'Blinding Lights',
    artist: 'The Weeknd',
    album: 'After Hours',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 200,
    durationMs: 200000,
    artwork: 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=600&auto=format&fit=crop&q=80',
    query: 'Blinding Lights The Weeknd',
    offlineReady: true
  },
  {
    id: 'off_51',
    title: 'Starboy',
    artist: 'The Weeknd, Daft Punk',
    album: 'Starboy',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 230,
    durationMs: 230000,
    artwork: 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=600&auto=format&fit=crop&q=80',
    query: 'Starboy The Weeknd',
    offlineReady: true
  },
  {
    id: 'off_52',
    title: 'Stay',
    artist: 'The Kid LAROI, Justin Bieber',
    album: 'F*CK LOVE 3',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 141,
    durationMs: 141000,
    artwork: 'https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=600&auto=format&fit=crop&q=80',
    query: 'Stay The Kid LAROI Justin Bieber',
    offlineReady: true
  },
  {
    id: 'off_53',
    title: 'Night Changes',
    artist: 'One Direction',
    album: 'FOUR',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 226,
    durationMs: 226000,
    artwork: 'https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=600&auto=format&fit=crop&q=80',
    query: 'Night Changes One Direction',
    offlineReady: true
  },
  {
    id: 'off_54',
    title: 'Dandelions',
    artist: 'Ruth B.',
    album: 'Safe Haven',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 233,
    durationMs: 233000,
    artwork: 'https://images.unsplash.com/photo-1490750967868-88aa4486c946?w=600&auto=format&fit=crop&q=80',
    query: 'Dandelions Ruth B',
    offlineReady: true
  },
  {
    id: 'off_55',
    title: 'Memories',
    artist: 'Maroon 5',
    album: 'JORDI',
    genre: 'global',
    genreLabel: 'Global Hits',
    durationSec: 189,
    durationMs: 189000,
    artwork: 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=600&auto=format&fit=crop&q=80',
    query: 'Memories Maroon 5',
    offlineReady: true
  }
];

if (typeof window !== 'undefined') {
  window.SONIC_CATALOG = SONIC_CATALOG;
  window.SONIC_OFFLINE_SONGS = SONIC_OFFLINE_SONGS;
}

if (typeof module !== 'undefined') {
  module.exports = { SONIC_CATALOG, SONIC_OFFLINE_SONGS };
}
