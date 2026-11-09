const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const PUBLIC_HTML = path.join(__dirname, 'public/index.html');
const DRAWABLE_DIR = path.join(__dirname, 'app/src/main/res/drawable');
const APK_PATH = path.join(__dirname, 'app/build/outputs/apk/debug/app-debug.apk');

const server = http.createServer((req, res) => {
  // Static route for drawables
  if (req.url.startsWith('/drawable/')) {
    const filename = path.basename(req.url);
    const filepath = path.join(DRAWABLE_DIR, filename);
    if (fs.existsSync(filepath)) {
      const ext = path.extname(filepath).toLowerCase();
      const contentType = ext === '.png' ? 'image/png' : 'image/jpeg';
      res.writeHead(200, { 'Content-Type': contentType, 'Cache-Control': 'public, max-age=86400' });
      return fs.createReadStream(filepath).pipe(res);
    }
  }

  // APK download route
  if (req.url === '/app-debug.apk') {
    if (fs.existsSync(APK_PATH)) {
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Disposition': 'attachment; filename="Nhonguista-Internacional.apk"'
      });
      return fs.createReadStream(APK_PATH).pipe(res);
    }
  }

  // Serve main HTML app
  if (fs.existsSync(PUBLIC_HTML)) {
    res.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8',
      'Cache-Control': 'no-cache'
    });
    return fs.createReadStream(PUBLIC_HTML).pipe(res);
  }

  res.writeHead(404, { 'Content-Type': 'text/plain' });
  res.end('Not Found');
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Nhonguista Internacional server listening on port ${PORT}`);
});
