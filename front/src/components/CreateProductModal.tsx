import { useState } from 'react'
import { toast } from 'react-toastify'
import { createProduct } from '../api/hooks/products.service'
import { updateInventoryStock } from '../api/hooks/inventory-stock.service'

interface Props {
  onClose: () => void
  onSuccess: () => void
}

const CreateProductModal = ({ onClose, onSuccess }: Props) => {
  const [loading, setLoading] = useState(false)

  const [form, setForm] = useState({
    name: '',
    price: 0,
    description: '',
    state: 'AVAILABLE',
    stock: 0,
    image: null as File | null,
  })

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!form.image) {
      toast.warn('Debe seleccionar una imagen')
      return
    }

    try {
      setLoading(true)

      // 1️⃣ Crear producto
      const res = await createProduct({
        name: form.name,
        price: form.price,
        description: form.description,
        state: form.state,
        image: form.image,
      })

      const productId = res.data.data.id
      if (!productId) {
        throw new Error('No se recibió productId')
      }

      // 2️⃣ Crear / actualizar stock
      await updateInventoryStock(productId, form.stock)

      toast.success('Producto creado con stock 🎉')

      onSuccess()
      onClose()
    } catch (error: any) {
      console.error(error)
      toast.error(
        error.response?.data?.message ||
          'Error al crear el producto'
      )
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Crear producto</h2>

        <form onSubmit={handleSubmit}>

          <div>
            <label className='form-label'>Nombre</label>
            <input
              className='form-input'
              placeholder="Nombre"
              value={form.name}
              onChange={(e) =>
                setForm({ ...form, name: e.target.value })
              }
              required
            />
          </div>

          <div>
            <label className='form-label'>Precio</label>
            <input
              className='form-input'
              type="number"
              placeholder="Precio"
              value={form.price}
              onChange={(e) =>
                setForm({ ...form, price: Number(e.target.value) })
              }
              required
            />
          </div>

          <div>
            <label className='form-label'>Descripción</label>
            <textarea
              className='form-input'
              placeholder="Descripción"
              value={form.description}
              onChange={(e) =>
                setForm({ ...form, description: e.target.value })
              }
              required
            />
          </div>

          <div>
            <label className='form-label'>Estado</label>
            <select
              className='form-select'
              value={form.state}
              onChange={(e) =>
                setForm({ ...form, state: e.target.value })
              }
            >
              <option value="AVAILABLE">AVAILABLE</option>
              <option value="UNAVAILABLE">UNAVAILABLE</option>
            </select>
          </div>

          <div>
            <label className='form-label'>Stock inicial"</label>
            <input
              className='form-input'
              type="number"
              placeholder="Stock inicial"
              min={0}
              value={form.stock}
              onChange={(e) =>
                setForm({ ...form, stock: Number(e.target.value) })
              }
              required
            />
          </div>

          <div>
            <label className='form-label'>image"</label>
            <input
              className='form-input'
              type="file"
              accept="image/*"
              onChange={(e) =>
                setForm({
                  ...form,
                  image: e.target.files?.[0] || null,
                })
              }
              required
            />
          </div>

          <div className="modal-actions">
            <button className='login-button sentex' type="button" onClick={onClose}>
              Cancelar
            </button>
            <button className='login-button sentex' type="submit" disabled={loading}>
              {loading ? 'Creando...' : 'Crear'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default CreateProductModal
