FROM openjdk:17-slim-buster

ENV TZ="Asia/Almaty"
ENV ADM_RELEASE_VERSION {{RELEASE_VERSION}}

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} adm.jar
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:InitialRAMPercentage=20","-XX:MaxRAMPercentage=75","-jar","/adm.jar"]
