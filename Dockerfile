FROM maven:3.9.8-amazoncorretto-21-al2023
LABEL authors="reiss"
WORKDIR /app
COPY pom.xml .
COPY ~/.m2 /root/.m2
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -Dskiptests
RUN echo "done"

FROM amazoncorretto:21.0.3-al2023-headless
WORKDIR /app
LABEL maintainer="gerry.sheva@proton.me"
COPY ./target/*.jar app.jar
EXPOSE ${APP_PORT}
ENTRYPOINT ["java","-jar","app.jar"]