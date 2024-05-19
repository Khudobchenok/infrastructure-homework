FROM --platform=linux/arm64 arm64v8/openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} people.jar
ENTRYPOINT ["java","-jar","/people.jar"]