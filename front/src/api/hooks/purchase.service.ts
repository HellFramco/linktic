import apiInventory from '../axiosInventory'

interface PurchasePayload {
  productId: string
  quantity: number
}

export const purchaseProduct = (payload: PurchasePayload) => {
  return apiInventory.post('/purchases', payload)
}
