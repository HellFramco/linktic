/// <reference types="vite/client" />  // ← importante: hereda los tipos base de Vite

interface ImportMetaEnv {
  readonly VITE_API_URL: string;
  // Agrega aquí todas tus otras variables de entorno que empiecen con VITE_
  // Ejemplos:
//   readonly VITE_API_URL: string;
  // readonly VITE_SOME_KEY: number;
  // readonly VITE_FEATURE_FLAG: boolean;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}