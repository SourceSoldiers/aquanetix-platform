# --- Paso 1: Compilar la aplicación usando el JDK 26 oficial ---
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Instalar Maven dentro del contenedor de compilación
RUN apt-get update && apt-get install -y maven

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar y generar el .jar
RUN mvn clean package -DskipTests

# --- Paso 2: Crear la imagen final de ejecución ligera ---
FROM eclipse-temurin:26-jre
WORKDIR /app

# Copiar el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]