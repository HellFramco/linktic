// src/App.tsx
import { Routes, Route } from 'react-router-dom'
import Home from '@pages/Home'
import Products from '@pages/Products'
import NotFound from '@pages/NotFound'
import Inventory from '@pages/InventoryPage'
import Diagramas from '@pages/Diagramas'
import { ToastContainer } from 'react-toastify'


import 'react-toastify/dist/ReactToastify.css'
import './index.css'

import Menu from './components/menu';

function App() {
  return (
    <>
      <ToastContainer position="top-right" autoClose={3000} />
      <Menu />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/products" element={<Products />} />
        <Route path="*" element={<NotFound />} />
        <Route path="/inventory" element={<Inventory />} />
        <Route path="/diagramas" element={<Diagramas />} />
      </Routes>
    </>
  )
}

export default App