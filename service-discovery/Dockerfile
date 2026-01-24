FROM eclipse-temurin:21-jdk

ARG JAR_FILE=./build/libs/*.jar

EXPOSE 8761

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]


# docker run -p 8761:8761 --name helpdesk-service-discovery -e CONFIG_SERVER_URI=:http://localhost:8888 -t felipesousagomes/helpdesk-service-discovery:latest