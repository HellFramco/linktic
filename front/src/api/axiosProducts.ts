// src/api/axiosProducts.ts
import axios from 'axios'

const apiProducts = axios.create({
  baseURL: "/api/products",
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'X-API-KEY': 'producto-api-linktic',
  },
})

apiProducts.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export default apiProducts