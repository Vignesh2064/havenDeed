## Start with a base image containing Java runtime
#FROM openjdk:17-jdk-slim
#
## Expose the application port
#EXPOSE 8080
#
## Add the application's jar file
#ARG JAR_FILE=target/havendeed.jar
#COPY ${JAR_FILE} app.jar
#
## Run the jar file
#ENTRYPOINT ["java", "-jar","/app.jar"]

#FROM maven:3.8.3-jdk-11 AS build
#COPY . /app
#WORKDIR /app
#RUN mvn package -DskipTests

# # Second stage: create a slim image
# FROM openjdk:17-jdk-slim

# # COPY src/main/resources/application.properties ./application.properties

# ARG JAR_FILE=target/havendeed.jar
# COPY ${JAR_FILE} app.jar

# EXPOSE 8080
# ENTRYPOINT exec java -jar app.jar
# # ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/application.properties"]

FROM openjdk:8u151-jdk-alpine3.7
  
EXPOSE 8080
 
ENV APP_HOME /usr/src/app

COPY target/havendeed.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

ENTRYPOINT exec java -jar app.jar
