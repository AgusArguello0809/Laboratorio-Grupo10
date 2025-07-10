# Imagen base de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Crear directorio de trabajo
WORKDIR /app

# Copiar el .jar compilado al contenedor
COPY target/*.jar app.jar

# Exponer el puerto que usa Spring Boot (por defecto es el 8080)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]