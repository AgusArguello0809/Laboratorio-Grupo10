# Dockerfile para el backend (Spring Boot)
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
