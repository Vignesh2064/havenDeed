FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/havendeed.jar /app/havendeed.jar

EXPOSE 7072

CMD ["java", "-jar", "havendeed.jar"]
