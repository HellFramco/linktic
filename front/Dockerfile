# Etapa 1: Build del frontend (Vite + React + TS)
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --frozen-lockfile
COPY . .
RUN npm run build

# Etapa 2: Nginx + assets + config
FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html

# Copia tu configuración (asegúrate de que nginx.conf esté en el context del build)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# NO hagas rm aquí – elimina el default.conf de la imagen base si quieres, pero ANTES de copiar el tuyo
# RUN rm -f /etc/nginx/conf.d/default.conf   ← comenta o quita esta línea

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]