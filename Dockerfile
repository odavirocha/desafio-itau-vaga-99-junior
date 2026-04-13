FROM maven:3.9.14-eclipse-temurin-17-alpine AS build
WORKDIR /build
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 3030
CMD ["java", "-jar", "app.jar"]

