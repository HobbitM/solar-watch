# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim-buster

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/solar-watch-0.0.1-SNAPSHOT.jar /app/

# Specify the command to run on container start
CMD ["java", "-jar", "solar-watch-0.0.1-SNAPSHOT.jar"]