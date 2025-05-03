# Etapa 1: Build da aplicação
FROM eclipse-temurin:24-jdk AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de build (pom.xml ou build.gradle) e dependências
COPY pom.xml ./
COPY src ./src

# Instala o Maven (caso use Maven) e faz o build
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Etapa 2: Imagem final otimizada
FROM eclipse-temurin:24-jre AS runtime

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Define variáveis de ambiente para otimização do Spring Boot
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]