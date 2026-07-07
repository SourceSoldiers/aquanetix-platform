# --- Paso 1: Compilar la aplicación ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el archivo de configuración de Maven y el código fuente
COPY pom.xml .
COPY src ./src

# Compilar el proyecto saltándose los tests (para agilizar el despliegue)
RUN mvn clean package -DskipTests

# --- Paso 2: Crear la imagen de ejecución ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]