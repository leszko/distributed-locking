FROM gradle:jdk11 as builder
USER root
WORKDIR /home/gradle/project
COPY . /home/gradle/project
RUN gradle build

FROM openjdk:11-jre-slim
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]