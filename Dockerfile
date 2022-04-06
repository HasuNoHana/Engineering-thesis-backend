#FROM adoptopenjdk/openjdk11:alpine-jre
#MAINTAINER ZuzannaSantorowska
#RUN mvn clean package
#COPY ./target/common-backend-0.0.1-SNAPSHOT.jar common-backend-1.0.0.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/common-backend-1.0.0.jar"]

#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]