# Etapa 1: Build da aplicação com Maven (usando uma imagem com Maven instalado)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Criar imagem leve somente com o JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o JAR da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta definida no application.properties
EXPOSE 8080

ENV LANG C.UTF-8
ENV TZ America/Sao_Paulo
# Instrução para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
