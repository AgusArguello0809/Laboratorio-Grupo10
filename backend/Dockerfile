# Etapa 1: build con Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar los archivos necesarios y construir el proyecto
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: runtime con JDK liviano
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el .jar compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
