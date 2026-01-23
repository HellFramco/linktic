// src/pages/InventoryPage.tsx
import { useEffect, useState } from 'react'
import { getInventoryProducts } from '../api/inventory.service'
import { InventoryProduct } from '../types/inventory'
import ProductCard from '../components/ProductCard'

const InventoryPage = () => {
  const [products, setProducts] = useState<InventoryProduct[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getInventoryProducts()
      .then((res) => {
        setProducts(res.data.content)
      })
      .catch(console.error)
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <p>Cargando productos...</p>

  return (
    <div className="grid">
      {products.map((product) => (
        <ProductCard key={product.productId} product={product} />
      ))}
    </div>
  )
}

export default InventoryPage
