import apiProducts from '../axiosProducts'

export const getProductById = async (productId: string) => {
  const { data } = await apiProducts.get(`/products/${productId}`)
  return data
}


export const updateProduct = async (
  productId: string,
  payload: FormData
) => {
  const { data } = await apiProducts.patch(`/products/${productId}`, payload, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
  return data
}
