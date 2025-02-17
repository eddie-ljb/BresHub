FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
EXPOSE 8084
COPY build/libs/breshub-engine-0.0.1-SNAPSHOT.jar breshub-engine-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/breshub-engine-0.0.1-SNAPSHOT.jar","--server.port=8084"]
