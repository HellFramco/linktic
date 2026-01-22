// src/types/index.ts
export interface Product {
  id: string | number
  name: string
  price: number
  description?: string
  stock?: number
  category?: string
  imageUrl?: string
}

export interface ApiResponse<T> {
  success: boolean
  data?: T
  error?: string
  message?: string
}

// Agrega más interfaces según necesites (User, Cart, etc.)