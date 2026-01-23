// src/api/inventory.service.ts
import apiInventory from '../axiosInventory'
export const getInventoryProducts = () => {
  return apiInventory.get('/inventory/products', {
    params: {
      page: 0,
      size: 20,
      sortBy: 'id',
      direction: 'asc',
    }
  })
}
