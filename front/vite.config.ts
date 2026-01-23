import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tsconfigPaths from 'vite-tsconfig-paths'
import path from 'node:path'

export default defineConfig({
  plugins: [
    react(),
    tsconfigPaths(),
  ],

  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },

  server: {
    port: 3000,
    open: true,
    // proxy: {
    //   '/api-products': {
    //     target: 'http://localhost:8081',
    //     changeOrigin: true,
    //     secure: false,
    //     rewrite: (path) => path.replace(/^\/api-products/, ''),
    //   },
    //   '/api-inventory': {
    //     target: 'http://localhost:8082',
    //     changeOrigin: true,
    //     secure: false,
    //     rewrite: (path) => path.replace(/^\/api-inventory/, ''),
    //   },
    // },
  },

  build: {
    outDir: 'dist',
    sourcemap: true,
  },
})