# Etapa 1: Build
FROM node:20-alpine as build

WORKDIR /app

# Copiar solo lo necesario primero para aprovechar la cache de Docker
COPY package.json package-lock.json ./
RUN npm install --frozen-lockfile

# Copiar el resto del código, incluyendo .env.production
COPY . .

# Cargar la variable para el build
ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL

# Vite usa automáticamente .env.production para el build
RUN npm run build

# Etapa 2: NGINX para servir el build
FROM nginx:alpine

# Borrar archivos por defecto del NGINX
RUN rm -rf /usr/share/nginx/html/*

# Copiar el build desde la etapa anterior
COPY --from=build /app/dist /usr/share/nginx/html

# Exponer el puerto estándar de NGINX
EXPOSE 80

# Arrancar NGINX
CMD ["nginx", "-g", "daemon off;"]