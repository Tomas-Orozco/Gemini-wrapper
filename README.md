# Gemini-wrapper
API Gemini usage
Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.4
- Thymeleaf (para UI)
- MySQL
- Google Gemini API
- Docker
- Maven

Requisitos previos

- Java 17+
- Maven 3.x
- Docker Desktop
- Base de datos MySQL corriendo localmente
- Archivo `application-secrets.properties` en `src/main/resources/` donde se declara la contrasena de la Base de Dats y el Token de Gemini

   `application.properties`

```properties
spring.application.name=gemeni-wrapper-tomas
server.port=8080

spring.datasource.url=jdbc:mysql://host.docker.internal:3306/gemini_chat_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent

spring.config.import=optional:classpath:application-secrets.properties

spring.datasource.password=TU_CONTRASEÑA
gemini.api.key=TU_API_KEY

Opcion 1 para correr el programa:
mvn clean package -DskipTests
java -jar target/gemeni-wrapper-0.0.1-SNAPSHOT.jar

opcion 2:
docker build -t gemeni-wrapper .
docker run -p 8080:8080 gemeni-wrapper

Base de datos:
El Archivo dump de la base de datos se encuentra en dumpBD.
