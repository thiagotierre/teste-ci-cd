FROM maven:3-amazoncorretto-21 AS maven
WORKDIR /usr/src/app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:21-oracle
WORKDIR /usr/src/app
COPY --from=maven /usr/src/app/target/challenge.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","app.jar", "-Xms512M", "-Xmx1024M"]