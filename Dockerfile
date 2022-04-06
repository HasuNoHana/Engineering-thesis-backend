FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ZuzannaSantorowska
COPY ./target/common-backend-0.0.1-SNAPSHOT.jar message-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]