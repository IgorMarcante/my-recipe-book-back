# Usa uma imagem base do Ubuntu (Debian-based)
FROM ubuntu:22.04

# Instala dependências e configura o repositório do Corretto
RUN apt-get update && \
    apt-get install -y wget gnupg software-properties-common && \
    wget -O - https://apt.corretto.aws/corretto.key | gpg --dearmor -o /usr/share/keyrings/corretto-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/corretto-keyring.gpg] https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list && \
    apt-get update && \
    apt-get install -y java-24-amazon-corretto-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR da aplicação
COPY target/*.jar app.jar

# Expõe a porta e executa a aplicação
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]