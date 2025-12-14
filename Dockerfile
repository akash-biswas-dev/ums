FROM node:24.12.0-bookworm AS client_builder

WORKDIR /client

COPY client/ .

RUN ["npm","install"]

RUN ["npm","run","build"]

FROM maven:3.9.11-eclipse-temurin-21 AS server_builder

WORKDIR /app

COPY server/ .

COPY --from=client_builder client/dist/ src/main/resources/static

RUN ["mvn","clean","package","-DskipTests"]

FROM openjdk:26-ea-21-slim 

WORKDIR /app

COPY --from=server_builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
