import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import mkcert from 'vite-plugin-mkcert'
import fs from 'fs';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    mkcert()
  ],
  server: { https: true },
  https: {
      key: fs.readFileSync("../VUE/localhost-key.pem"),
      cert: fs.readFileSync("../VUE/localhost.pem"),
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
