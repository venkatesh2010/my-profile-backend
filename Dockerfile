# Stage 1: Build the application using Maven
# We use a JDK image that matches your project's Java version (Java 21)
FROM eclipse-temurin:21-jdk-jammy AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files and the pom.xml
# This allows us to download dependencies first and leverage Docker caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application, creating the executable JAR. Skip tests for faster image builds.
RUN ./mvnw package -DskipTests -B

# Stage 2: Create the final lightweight runtime image
# We use a JRE image as it's smaller than a JDK image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the executable JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]