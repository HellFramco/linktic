import { useState } from 'react'
import { InventoryProduct } from '../types/inventory'
import { purchaseProduct } from '../api/hooks/purchase.service'
import { deleteProduct } from '../api/hooks/productsDeleted.service'
import { getProductById, updateProduct } from '../api/hooks/productsUpdate.service'
import { toast } from 'react-toastify'

interface Props {
  product: InventoryProduct
  onPurchaseSuccess: () => void
  onDeleteSuccess?: () => void
}

export const formatCOP = (value: number) =>
  new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    minimumFractionDigits: 0,
  }).format(value)

const ProductCard = ({ product, onPurchaseSuccess, onDeleteSuccess }: Props) => {
  const [quantity, setQuantity] = useState(1)
  const [loading, setLoading] = useState(false)
  const [deleting, setDeleting] = useState(false)

  const [isEditOpen, setIsEditOpen] = useState(false)
  const [editLoading, setEditLoading] = useState(false)

  const [editName, setEditName] = useState('')
  const [editPrice, setEditPrice] = useState(0)
  const [editDescription, setEditDescription] = useState('')
  const [editState, setEditState] = useState<'AVAILABLE' | 'UNAVAILABLE'>('AVAILABLE')
  const [editImage, setEditImage] = useState<File | null>(null)

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
      onPurchaseSuccess()
    } catch (error: any) {
      console.error(error)
      toast.error(
        error.response?.data?.message || 'Error al realizar la compra'
      )
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    try {
      setDeleting(true)

      await deleteProduct(product.productId)

      toast.success('Producto eliminado correctamente 🗑️')
      onDeleteSuccess?.()
      onPurchaseSuccess()
    } catch (error: any) {
      console.error(error)
      toast.error(
        error.response?.data?.message || 'Error al eliminar el producto'
      )
    } finally {
      setDeleting(false)
    }
  }

  const openEdit = async () => {
    try {
      setIsEditOpen(true)
      const res: any = await getProductById(product.productId)

      const attrs = res.data.attributes

      setEditName(attrs.name)
      setEditPrice(attrs.price)
      setEditDescription(attrs.description)
      setEditState(attrs.state)
    } catch (error: any) {
      toast.error('Error al cargar el producto')
      setIsEditOpen(false)
    }
  }

  const handleEditSave = async () => {
    try {
      setEditLoading(true)

      const formData = new FormData()
      formData.append('name', editName)
      formData.append('price', String(editPrice))
      formData.append('description', editDescription)
      formData.append('state', editState)

      if (editImage) {
        formData.append('image', editImage)
      }

      await updateProduct(product.productId, formData)

      toast.success('Producto editado correctamente ✨')
      setIsEditOpen(false)
      onPurchaseSuccess()
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Error al editar el producto')
    } finally {
      setEditLoading(false)
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

          <button
            onClick={openEdit}
            style={{ marginLeft: 10 }}
          >
            EDIT
          </button>

          <button
            onClick={handleDelete}
            disabled={deleting}
            style={{ marginLeft: 10 }}
          >
            {deleting ? 'Eliminando...' : 'DELETE'}
          </button>
        </div>
      </div>
      {isEditOpen && (
        <div className="modal">
          <div className="modal-content">
            <h2>Editar producto</h2>

            <div>
              <label>Nombre</label>
              <input value={editName} onChange={(e) => setEditName(e.target.value)} />
            </div>

            <div>
              <label>Precio</label>
              <input
                type="number"
                value={editPrice}
                onChange={(e) => setEditPrice(Number(e.target.value))}
              />
            </div>

            <div>
              <label>Descripción</label>
              <textarea
                value={editDescription}
                onChange={(e) => setEditDescription(e.target.value)}
              />
            </div>

            <div>
              <label>Estado</label>
              <select value={editState} onChange={(e) => setEditState(e.target.value as any)}>
                <option value="AVAILABLE">AVAILABLE</option>
                <option value="UNAVAILABLE">UNAVAILABLE</option>
              </select>
            </div>

            <div>
              <label>Imagen</label>
              <input type="file" onChange={(e) => setEditImage(e.target.files?.[0] || null)} />
            </div>

            <div className="modal-actions">
              <button onClick={() => setIsEditOpen(false)}>Cancelar</button>
              <button onClick={handleEditSave} disabled={editLoading}>
                {editLoading ? 'Guardando...' : 'Guardar'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default ProductCard
