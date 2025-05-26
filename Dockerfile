FROM eclipse-temurin:17-jdk-alpine
# 경량 이미지라 다중 환경을 지원하지 않음.
# macOS 로컬에서 docker build 시 `--platform=linux/amd64` 옵션 추가 필요

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
