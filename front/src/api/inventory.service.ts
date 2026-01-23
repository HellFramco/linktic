// src/api/inventory.service.ts
import api from './axios'

export const getInventoryProducts = () => {
  return api.get('/inventory/products', {
    params: {
      page: 0,
      size: 20,
      sortBy: 'id',
      direction: 'asc',
    },
    headers: {
      'X-API-KEY': 'inventory-api-linktic',
    },
  })
}
