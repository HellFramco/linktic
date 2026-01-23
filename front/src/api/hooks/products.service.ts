import apiProducts from '../axiosProducts'

export interface CreateProductPayload {
  name: string
  price: number
  description: string
  state: string
  image: File
}

export const createProduct = (payload: CreateProductPayload) => {
  const formData = new FormData()

  formData.append('name', payload.name)
  formData.append('price', String(payload.price))
  formData.append('description', payload.description)
  formData.append('state', String(payload.state))
  formData.append('image', payload.image)

  return apiProducts.post('/products', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}
