// src/api/axiosInventory.ts
import axios from 'axios'

const apiInventory = axios.create({
  baseURL: "/api/inventory",
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'X-API-KEY': 'inventory-api-linktic',
  },
})

apiInventory.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export default apiInventory