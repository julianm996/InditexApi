# Etapa 1: Construcción
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar solo archivos necesarios primero (para cachear)
COPY pom.xml .

# Descargar dependencias sin compilar
RUN mvn dependency:go-offline

# Copiar el resto del proyecto
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/priceApi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
