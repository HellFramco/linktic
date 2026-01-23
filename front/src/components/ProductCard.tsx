// src/components/ProductCard.tsx
import { InventoryProduct } from '../types/inventory'

interface Props {
  product: InventoryProduct
}

const ProductCard = ({ product }: Props) => {
  return (
    <div className="card">
      <img
        src={`/images/${product.imageUrl}`}
        alt={product.name}
        className="card-img"
      />

      <div className="card-body">
        <h3>{product.name}</h3>
        <p>{product.description}</p>
        <p><strong>Precio:</strong> ${product.price}</p>
        <p><strong>Stock:</strong> {product.quantity}</p>
        <span className={`badge ${product.state.toLowerCase()}`}>
          {product.state}
        </span>
      </div>
    </div>
  )
}

export default ProductCard
