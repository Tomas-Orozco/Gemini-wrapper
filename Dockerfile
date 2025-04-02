
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

COPY src/main/resources/application-secrets.properties /app/application-secrets.properties

ENTRYPOINT ["java", "-Dspring.config.import=optional:classpath:/application-secrets.properties", "-jar", "app.jar"]
