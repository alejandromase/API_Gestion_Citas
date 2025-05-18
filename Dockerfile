# Usa una imagen base de Java (por ejemplo, OpenJDK 17)
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/API_Gestion_Citas-0.0.1-SNAPSHOT.jar /app/API_Gestion_Citas-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que se ejecuta tu aplicación (ajusta según tu configuración)
EXPOSE 8080

# Define el comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "API_Gestion_Citas-0.0.1-SNAPSHOT.jar"]
