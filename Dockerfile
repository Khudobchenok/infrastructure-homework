FROM arm64v8/openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} people.jar
ENTRYPOINT ["java","-jar","/people.jar"]