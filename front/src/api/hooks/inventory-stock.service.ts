import apiInventory from '../axiosInventory'

export const updateInventoryStock = (
  productId: string,
  quantity: number
) => {
  return apiInventory.put(`/inventory/${productId}`, {
    quantity,
  })
}
