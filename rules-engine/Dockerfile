# Use official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy JAR file from target directory
COPY target/rules-engine-*-SNAPSHOT.jar rules-engine.jar

# Run the application
ENTRYPOINT ["java", "-jar", "rules-engine.jar"]
