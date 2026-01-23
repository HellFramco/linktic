// src/types/inventory.ts
export interface InventoryProduct {
  productId: string
  name: string
  description: string
  price: number
  imageUrl: string
  quantity: number
  state: string
}

export interface InventoryResponse {
  content: InventoryProduct[]
}
