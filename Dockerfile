# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .

# Download dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user for security
RUN useradd -m appuser && chown -R appuser /app
USER appuser

# Expose port 8080 (for Render compatibility)
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Set default environment variables (configurable at runtime)
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xms512m -Xmx1024m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]