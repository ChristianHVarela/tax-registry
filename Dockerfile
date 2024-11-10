FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /tax-registry-app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
RUN mkdir /tax-registry-app
WORKDIR /tax-registry-app
COPY --from=build /tax-registry-app/target/*.jar /tax-registry-app/tax-registry-app.jar
CMD ["java", "-jar", "/tax-registry-app/tax-registry-app.jar"]