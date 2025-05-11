FROM maven:3.9.6-eclipse-temurin-21

RUN mkdir -p /app
WORKDIR /app

COPY pom.xml /app
COPY src/main/resources/application.properties /app
COPY src /app/src

RUN mvn -f pom.xml clean package -DskipTests

RUN cp target/*.jar app.jar
EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=application.properties"]
