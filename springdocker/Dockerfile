# Docker image for springboot file run
# 基础镜像使用java
FROM openjdk:8-jdk-alpine


# 将jar包添加到容器中并更名为app.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar


# 运行jar包
ENTRYPOINT ["java","-jar","/app.jar"]