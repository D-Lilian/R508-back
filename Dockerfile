# test image
FROM maven:3.9.9-amazoncorretto-21 AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean install -DskipTests=false -DskipITs -B

# build image
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
