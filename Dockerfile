FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

# Use Eclipse Temurin's Java 17 image as the base image
FROM eclipse-temurin:17-jdk

# Copy the executable JAR file into the Docker image
COPY --from=builder /app/target/*.jar /app/server.jar

# Set working directory inside the container
WORKDIR /app

# Expose the port on which your Spring Boot app is running
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "server.jar"]