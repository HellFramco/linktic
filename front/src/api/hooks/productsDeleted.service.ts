import apiProducts from '../axiosProducts'

export interface CreateProductPayload {
  name: string
  price: number
  description: string
  state: string
  image: File
}

export const deleteProduct = async (productId: string) => {
  const { data } = await apiProducts.delete(`/products/${productId}`)
  return data
}
