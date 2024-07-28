FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceregistry.jar

#for running anything in between or any other command we use CMD
#for runnin anything at start command we give it at entry point

ENTRYPOINT ["java","-jar", "/serviceregistry.jar"]

EXPOSE 8761
