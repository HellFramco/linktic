// src/pages/Home.tsx
export default function Home() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
      <h1 className="text-4xl font-bold text-blue-600 mb-4">Bienvenido a tu App</h1>
      <p className="text-lg text-gray-600 mb-8">Frontend React + TypeScript + Vite</p>
      <a
        href="/products"
        className="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
      >
        Ver Productos
      </a>
    </div>
  )
}