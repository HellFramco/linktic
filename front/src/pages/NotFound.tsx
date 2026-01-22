// src/pages/NotFound.tsx
export default function NotFound() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <h1 className="text-6xl font-bold text-red-500">404</h1>
      <p className="text-2xl mt-4">Página no encontrada</p>
      <a href="/" className="mt-6 text-blue-600 hover:underline">
        Volver al inicio
      </a>
    </div>
  )
}