#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine

ARG DEPENDENCY=/app/target

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY} /app/target
RUN ls /app/target

ENTRYPOINT ["java","-jar","/app/target/spring-boot-casandra-0.0.1-SNAPSHOT.jar"]
