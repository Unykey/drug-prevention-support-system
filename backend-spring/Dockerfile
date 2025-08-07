#FROM ubuntu:latest
#LABEL authors="long2"
#
#ENTRYPOINT ["top", "-b"]

# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copy everything to the container
COPY . .

# Build the app and skip tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Use the PORT env var injected by Render, default to 8080 if not set
ENV PORT=8080
EXPOSE ${PORT}

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
