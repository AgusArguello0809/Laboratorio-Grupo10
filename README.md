# FitStore - E-commerce (TPO UADE)

Proyecto fullstack de e-commerce desarrollado para la materia Aplicaciones Interactivas (UADE).  
Incluye frontend en React (Vite + Material UI) y backend en Spring Boot (Java 17, Maven) con base de datos MySQL (via XAMPP).

---

## 🚀 Cómo levantar el proyecto

### 📦 Backend (Spring Boot)

1. Asegurarse de tener:
   - Java 17
   - XAMPP corriendo con MySQL habilitado
   - Base de datos `fitstore` creada en MySQL (`localhost:3306`)
   - Usuario: `root`

2. Desde la carpeta /backend del proyecto, ejecutar:

mnv clean install

3. Ejecutar la app

El backend se levanta en http://localhost:8080

📄 Swagger: http://localhost:8080/fitstore-api/v1/swagger-ui.html (se puede visualizar una vez levantado)

### 💻 Frontend (React + Vite)
1. Ir a la carpeta del frontend
cd frontend

2. Instalar dependencias:

npm install

3. Levantar en modo desarrollo:

npm run dev

4. Abrir en el navegador:

http://localhost:5173

Asegurarse de que el frontend esté configurado para apuntar al backend (localhost:8080),
y que el backend esté levantado para la correcta conexión con el mismo.


### 🛠️ Notas útiles
Para resetear la base de datos, se puede usar el panel de phpMyAdmin de XAMPP
