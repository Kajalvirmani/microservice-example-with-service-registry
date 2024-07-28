FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configserver.jar

#for running anything in between or any other command we use CMD
#for runnin anything at start command we give it at entry point

ENTRYPOINT ["java","-jar", "/configserver.jar"]

EXPOSE 9296
