FROM openjdk:17-jdk-slim

WORKDIR /bet4u

COPY build/libs/Bet4U-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]