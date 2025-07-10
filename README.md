# FitStore - E-commerce (TPO UADE)

Proyecto fullstack de e-commerce desarrollado para la materia Aplicaciones Interactivas (UADE).  
Incluye frontend en React (Vite + Material UI) y backend en Spring Boot (Java 17, Maven) con base de datos MySQL (via XAMPP).

---

## 🚀 Cómo levantar el proyecto

### 🔥 Firebase - Configuración requerida

Para poder subir y visualizar imágenes desde el almacenamiento de Firebase (Storage), tanto en Docker como en local, se necesita el archivo de credenciales `firebase-key.json`.

1. Solicitar este archivo a un miembro del equipo.
2. Ubicarlo en la siguiente ruta: /backend/src/main/resources/firebase-key.json
3. El backend ya está configurado para leerlo desde ahí.

⚠️ **Este archivo no está incluido en el repositorio por motivos de seguridad.**

### 🐳 Usando Docker (recomendado)

1. Asegurarse de tener [Docker](https://www.docker.com/) y [Docker Compose](https://docs.docker.com/compose/) instalados.

2. Desde la raíz del proyecto, ejecutar:

docker compose build
docker compose up -d

O en su defecto:
docker compose up --build

El sistema estará disponible en:

Frontend: http://localhost:3000

Backend (Spring Boot): http://localhost:8080

Swagger UI: http://localhost:8080/fitstore-api/v1/swagger-ui.html

phpMyAdmin (para ver la base de datos): http://localhost:8081
Usuario: user
Contraseña: pass

ℹ️ phpMyAdmin accede directamente al contenedor de MySQL gracias al volumen compartido, por lo que se puede visualizar y gestionar la base fitstore fácilmente desde esa interfaz web.

Para detener y eliminar los contenedores:
docker compose down

Y si se desea eliminar también los volúmenes (por ejemplo, para resetear la base de datos):
docker compose down -v

🧩 Importar datos iniciales en la base
Dentro de la carpeta sql/ se encuentran archivos .sql con datos precargados.

Acceder a phpMyAdmin, seleccionar la base de datos fitstore y usar la pestaña Importar.

Seleccionar el archivo correspondiente y presionar Continuar.

💡 Si se levanta desde Docker por primera vez, el backend también precarga automáticamente las categorías básicas si están vacías.

📬 Postman - Colecciones y ambiente
Dentro de la carpeta postman/ se encuentran los siguientes archivos:

Colección: FitStore-Api.postman_collection.json

Ambiente: FitStore-Api.postman_environment.json

Importar en Postman:

Usar el botón Import y seleccionar ambos archivos.

Asegurarse de tener seleccionado el ambiente FitStore-Api.

⚠️ Importante: revisar los tokens almacenados en el ambiente. Verificar que:

Sigan siendo válidos (no expirados).

Coincidan con los roles adecuados (ADMIN, CLIENTE_1, etc.).

Se pueden regenerar con las credenciales en los endpoints de autenticación si fuera necesario.

### 📦 Backend (Spring Boot) (Sin Docker)

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

### 💻 Frontend (React + Vite) (Sin Docker)
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
