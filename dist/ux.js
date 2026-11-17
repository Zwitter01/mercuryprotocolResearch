/* SONiC UX layer: visual effects only. Never touches app state. */
(function () {
  'use strict';
  var $ = function (id) { return document.getElementById(id); };
  var root = document.documentElement;
  var canvas = $('mainCanvas');

  // Greeting
  var g = $('heroGreeting');
  if (g) {
    var h = new Date().getHours();
    g.textContent = h < 5 ? 'Late night session' : h < 12 ? 'Good morning' : h < 18 ? 'Good afternoon' : 'Good evening';
  }

  // Hero actions reuse existing UI
  var hp = $('heroPlayBtn');
  if (hp) hp.addEventListener('click', function () {
    var card = document.querySelector('#shelfBiggestHits .spotify-card, #shelfNewMusic .spotify-card, .cards-carousel-row .spotify-card');
    if (card) card.click();
  });
  var hn = $('heroNewBtn');
  if (hn) hn.addEventListener('click', function () {
    var shelf = $('shelfNewMusicBlock') || $('shelfNewMusic');
    if (shelf) shelf.scrollIntoView({ behavior: 'smooth' });
  });
  var ho = $('heroOfflineBtn');
  if (ho) ho.addEventListener('click', function () {
    var row = $('libOfflineRow');
    if (row) row.click();
  });

  // Scroll reveal for shelves (Desktop only for buttery-smooth 60fps mobile scrolling)
  var isMobile = window.innerWidth <= 900 || (document.body && document.body.classList.contains('android-app'));
  if ('IntersectionObserver' in window && !isMobile) {
    root.classList.add('js-reveal');
    var io = new IntersectionObserver(function (entries) {
      entries.forEach(function (e) {
        if (e.isIntersecting) { e.target.classList.add('in-view'); io.unobserve(e.target); }
      });
    }, { root: null, threshold: 0.02, rootMargin: '0px 0px 100px 0px' });
    var seen = new WeakSet();
    var scan = function () {
      document.querySelectorAll('.shelf-block, .spotify-catalog-footer, .detail-hero, .liked-header-card, .offline-header-card').forEach(function (el) {
        if (!seen.has(el)) {
          seen.add(el);
          io.observe(el);
          // Fallback: guarantee visibility
          setTimeout(function() { el.classList.add('in-view'); }, 300);
        }
      });
    };
    scan();
    if (canvas) new MutationObserver(scan).observe(canvas, { childList: true, subtree: true });
  }

  // Spotlight + 3D tilt on cards
  var CARD = '.spotify-card, .artist-card, .radio-card, .top-result-card';
  if (canvas && window.matchMedia('(pointer: fine)').matches) {
    var raf = 0;
    canvas.addEventListener('pointermove', function (ev) {
      var card = ev.target.closest && ev.target.closest(CARD);
      if (!card) return;
      cancelAnimationFrame(raf);
      raf = requestAnimationFrame(function () {
        var r = card.getBoundingClientRect();
        var x = ev.clientX - r.left, y = ev.clientY - r.top;
        card.style.setProperty('--mx', x + 'px');
        card.style.setProperty('--my', y + 'px');
        card.style.setProperty('--ry', ((x / r.width - 0.5) * 9).toFixed(2) + 'deg');
        card.style.setProperty('--rx', ((0.5 - y / r.height) * 9).toFixed(2) + 'deg');
      });
    });
    canvas.addEventListener('pointerout', function (ev) {
      var card = ev.target.closest && ev.target.closest(CARD);
      if (card && !card.contains(ev.relatedTarget)) {
        card.style.setProperty('--rx', '0deg');
        card.style.setProperty('--ry', '0deg');
      }
    });
  }

  // Playing state (equalizer visibility drives it)
  var eq = $('playerEqualizer');
  if (eq) {
    var sync = function () { document.body.classList.toggle('is-playing', !eq.classList.contains('hidden')); };
    new MutationObserver(sync).observe(eq, { attributes: true, attributeFilter: ['class'] });
    sync();
  }

  // Ambient light from the current artwork
  var art = $('playerArtwork');
  if (art) {
    var last = '';
    var tint = function () {
      var src = art.getAttribute('src');
      if (!src || src === last || src.indexOf('data:') === 0) return;
      last = src;
      var im = new Image();
      im.crossOrigin = 'anonymous';
      im.onload = function () {
        try {
          var c = document.createElement('canvas');
          c.width = c.height = 16;
          var x = c.getContext('2d');
          x.drawImage(im, 0, 0, 16, 16);
          var d = x.getImageData(0, 0, 16, 16).data, best = null, bs = -1;
          for (var i = 0; i < d.length; i += 4) {
            var r = d[i], gg = d[i + 1], b = d[i + 2];
            var mx = Math.max(r, gg, b), mn = Math.min(r, gg, b);
            var sat = mx === 0 ? 0 : (mx - mn) / mx;
            var score = sat * (mx / 255) + 0.02;
            if (mx > 50 && score > bs) { bs = score; best = [r, gg, b]; }
          }
          if (best) root.style.setProperty('--amb', best.join(','));
        } catch (e) { /* tainted canvas: keep default accent */ }
      };
      im.src = src;
    };
    new MutationObserver(tint).observe(art, { attributes: true, attributeFilter: ['src'] });
    tint();
  }
})();
