# ------------------ Etapa de build ------------------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
#Copiamos el pom para aprovechar la cache de dependencias
COPY pom.xml .
COPY src ./src
#Generamos el jar ejecutable omitiendo los tests (evitan depender de una base de datos)
RUN mvn -q -DskipTests package

# ------------------ Etapa de ejecucion ------------------
FROM eclipse-temurin:21-jre
WORKDIR /app
#Copiamos el jar generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar
#Puerto en el que escucha la aplicacion (SERVER_PORT)
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
