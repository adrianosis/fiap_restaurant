# Etapa de build
FROM maven:3.8.7 AS build

# Instale o OpenJDK 17 no contêiner
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Defina o diretório de trabalho e copie os arquivos da aplicação
WORKDIR /app
COPY . /app

# Execute o Maven para construir o JAR
RUN mvn clean package -DskipTests

# Etapa final: use o JDK para rodar a aplicação
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copie o JAR do estágio de build para o estágio final
COPY --from=build /app/target/restaurant-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponha a porta que a aplicação usará
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/app.jar"]
