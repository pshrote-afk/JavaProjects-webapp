#0: Build stage - compile the .war file with gradle
#Using gradle:8-jdk23 is cleaner because Gradle is already installed.
#The build stage is thrown away after building. Only the runtime stage (your Temurin + Tomcat) remains in the final image.
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app
COPY . .
# gradlew must be executable
RUN chmod +x gradlew
RUN ./gradlew clean build --no-daemon

#1: Using official Tomcat image with Java
#FROM tomcat:9-jdk17 - we want to do this, but we want tomcat:11-jdk23, which are not available as official Docker images yet. So we use the following two workarounds to build our own base images.

FROM eclipse-temurin:23-jdk

#install and unzip wget
RUN apt-get update && apt-get install -y wget unzip

#Download tomcat 11
RUN wget https://archive.apache.org/dist/tomcat/tomcat-11/v11.0.6/bin/apache-tomcat-11.0.6.tar.gz \
    && tar xzf apache-tomcat-11.0.6.tar.gz -C /opt \
    && mv /opt/apache-tomcat-11.0.6 /opt/tomcat

#2: copy .war into container's tomcat's webapps folder
COPY --from=build /app/build/libs/*.war /opt/tomcat/webapps/

#3: expose port 8080 for the the container to communicate
EXPOSE 8080

#4: when container starts, run tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]

