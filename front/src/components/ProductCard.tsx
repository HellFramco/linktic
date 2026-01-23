import { useState } from 'react'
import { InventoryProduct } from '../types/inventory'
import { purchaseProduct } from '../api/hooks/purchase.service'
import { toast } from 'react-toastify'

interface Props {
  product: InventoryProduct
  onPurchaseSuccess: () => void
}

export const formatCOP = (value: number) =>
  new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    minimumFractionDigits: 0,
  }).format(value)

const ProductCard = ({ product, onPurchaseSuccess }: Props) => {
  const [quantity, setQuantity] = useState(1)
  const [loading, setLoading] = useState(false)

  const handleBuy = async () => {
    if (quantity <= 0) {
      toast.warn('Cantidad inválida')
      return
    }

    try {
      setLoading(true)

      await purchaseProduct({
        productId: product.productId,
        quantity,
      })

      toast.success('Compra realizada con éxito 🛒')
      onPurchaseSuccess() // 🔄 refresca inventario
    } catch (error: any) {
      console.error(error)
      toast.error(
        error.response?.data?.message || 'Error al realizar la compra'
      )
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card">
      <div className="card__title">
        <div className="icon">
          <a href="#"><i className="fa fa-arrow-left"></i></a>
        </div>
        <h3>{product.state}</h3>
      </div>

      <div className="card__body">
        <div className="half">
          <div className="featured_text">
            <h1>LINKTIC</h1>
            <p className="sub">{product.name}</p>
            <p className="price">{formatCOP(product.price)}</p>
          </div>

          <div className="image">
            <img
              src={`/api/products/uploads/${product.imageUrl}`}
              alt={product.name}
              onError={(e) => {
                const target = e.currentTarget
                if (!target.dataset.fallback) {
                  target.dataset.fallback = 'true'
                  target.src =
                    'https://motobros.com/wp-content/uploads/2024/09/no-image.jpeg'
                }
              }}
            />
          </div>
        </div>

        <div className="half">
          <div className="description">
            <p>{product.description}</p>
          </div>

          <span className="stock">
            Stock disponible: {product.quantity}
          </span>

          <div style={{ marginTop: 10 }}>
            <label>Cantidad:</label>
            <input
              type="number"
              min={1}
              max={product.quantity}
              value={quantity}
              onChange={(e) => setQuantity(Number(e.target.value))}
            />
          </div>
        </div>
      </div>

      <div className="card__footer">
        <div className="recommend">
          <p>Recommended by</p>
          <h3>Manuel Barrios</h3>
        </div>

        <div className="action">
          <button onClick={handleBuy} disabled={loading}>
            {loading ? 'Comprando...' : 'BUY'}
          </button>
        </div>
      </div>
    </div>
  )
}

export default ProductCard
