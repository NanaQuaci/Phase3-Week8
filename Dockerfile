FROM maven:3.9.0-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM maven:3.9.0-eclipse-temurin-21
WORKDIR /app
COPY --from=builder /app/target /app/target
COPY pom.xml .
CMD ["mvn", "test", "-Dselenide.remote=http://selenium:4444/wd/hub"]
