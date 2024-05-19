FROM --platform=linux/amd64 openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} people.jar
ENTRYPOINT ["java","-jar","/people.jar"]