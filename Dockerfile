# Estágio 1: Compila o JAR
FROM amazoncorretto:24-alpine3.18-jdk as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package  # Ou comando do Gradle

# Estágio 2: Imagem final
FROM amazoncorretto:24-alpine3.18-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]