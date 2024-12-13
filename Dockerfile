# Use a base image com Java 17
FROM openjdk:17-jdk-slim

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo pom.xml e o diretório src para o contêiner
COPY pom.xml ./
COPY src ./src

# Baixar as dependências do Maven
RUN apt-get update && apt-get install -y maven && \
    mvn dependency:go-offline -B

# Compilar o aplicativo
RUN mvn package -DskipTests

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar o aplicativo
CMD ["java", "-jar", "target/content-upload-service-0.0.1-SNAPSHOT.jar"]
