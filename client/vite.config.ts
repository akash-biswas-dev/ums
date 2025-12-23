import tailwindcss from '@tailwindcss/vite'
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  server:{
    proxy:{
      "/backend":{
        target:"http://localhost:8501",
        changeOrigin:true,
        rewrite:(path) => path.replace(/^\/backend/, '')
      }
    }
  },
  plugins: [ 
    tailwindcss(),
    react({
      babel: {
        plugins: [['babel-plugin-react-compiler']],
      },
    }),
  ],
})
