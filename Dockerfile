
FROM eclipse-temurin:17-jdk-alpine
#FROM arm64v8/openjdk:17
RUN #chmod +x ./gradlew
RUN #./gradlew clean build -x test
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
