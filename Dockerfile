#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
ENV HOME=/home/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

#dodo remove me bo ping nie poczebny
RUN apt-get update && apt-get install -y iputils-ping

# EXPOSE 8080
# ENTRYPOINT ["sleep","1d"]
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]