FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR e-commerce

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p ./target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*jar
EXPOSE 8080
COPY ./target/ecomm.app-0.0.1-SNAPSHOT.jar ecomm-app.jar
ENTRYPOINT ["java","-jar","ecomm-app.jar"]

# docker build -t pranitdhage/e-comm-service:1.0 .
# docker run -p 8080:8080 pranitdhage/e-comm-service:1.0 --name=ecomm-app