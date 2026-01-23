// src/App.tsx
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from '@pages/Home'
import Products from '@pages/Products'
import NotFound from '@pages/NotFound'
import Inventory from '@pages/InventoryPage'
import { ToastContainer } from 'react-toastify'


import 'react-toastify/dist/ReactToastify.css'
import './index.css'

// import Menu from './components/menu';

function App() {
  return (
    <>
      <ToastContainer position="top-right" autoClose={3000} />
      {/* <Menu /> */}
      
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<Products />} />
          <Route path="*" element={<NotFound />} />
          <Route path="/inventory" element={<Inventory />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App