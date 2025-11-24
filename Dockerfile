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
COPY ./build/libs/JavaProjects-webapp.war /opt/tomcat/webapps/

#3: expose port 8080 for the the container to communicate
EXPOSE 8080

#4: when container starts, run tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]

