FROM openjdk:11-jre-slim
COPY build/libs/*.jar app.jar
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]