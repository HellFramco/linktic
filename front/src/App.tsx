// src/App.tsx
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from '@pages/Home'
import Products from '@pages/Products'
import NotFound from '@pages/NotFound'

import './index.css'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/products" element={<Products />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App