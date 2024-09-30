# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper (if you are using it)
COPY .mvn/ .mvn
COPY mvnw mvnw

# Copy the pom.xml file to the container
COPY pom.xml .

# Copy the source code into the container (this includes your Java and resource files)
COPY src/ src/

# Ensure the Maven wrapper has execute permissions
RUN chmod +x mvnw

# Run the Maven build to create the final JAR file
RUN ./mvnw clean package

# Expose the default Jetty port (8080)
EXPOSE 8080

# Run the Jetty server by launching the Java class containing the main method
CMD ["java", "-cp", "target/tinder.jar", "com.tinder.util.JettyRunner"]
