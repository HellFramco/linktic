import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import path from 'node:path';
// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tsconfigPaths(), // sincroniza automáticamente los paths de tsconfig.json
    ],
    resolve: {
        alias: {
            // opcional: redundante si usas tsconfigPaths, pero útil como fallback
            '@': path.resolve(__dirname, './src'),
        },
    },
    server: {
        port: 3000,
        open: true, // abre el navegador automáticamente
        proxy: {
            // Ejemplo para desarrollo (evita problemas CORS con backend)
            '/api': {
                target: 'http://localhost:8081', // apunta a uno de tus backends
                changeOrigin: true,
                secure: false,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
        },
    },
    build: {
        outDir: 'dist',
        sourcemap: true, // útil para debug en producción
    },
});
