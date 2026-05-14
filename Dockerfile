# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests


# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
# This line is more specific to avoid the "plain" jar
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
# Use this entrypoint to ensure we point to the right file
ENTRYPOINT ["java", "-jar", "app.jar"]