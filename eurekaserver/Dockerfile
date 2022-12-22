#start with a base image containing Java runtime
FROM openjdk:17-slim as build

#Information around who maintains the image
MAINTAINER eazybytes.com

#Add the application's jar to the container
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/eurekaserver-0.0.1-SNAPSHOT.jar"]
