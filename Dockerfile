FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/breshub-engine-0.0.1-SNAPSHOT.jar breshub-engine-0.0.1-SNAPSHOT.jar
ENV DATABASE_PASSWORD="AVNS_ZowLrPEs2We4NQLgXXi"
EXPOSE 8084
ENTRYPOINT ["java","-jar","/breshub-engine-0.0.1-SNAPSHOT.jar","--server.port=8084"]
