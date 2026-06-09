# 使用轻量级 JRE 17 基础镜像
FROM eclipse-temurin:17-jre-alpine

# 工作目录
WORKDIR /app

# 把打包好的 jar 复制到镜像中
COPY test-service-1.0-SNAPSHOT.jar /app/app.jar

# 暴露应用端口（根据你的 application.yml 中配置的端口修改，默认 8080）
EXPOSE 8080

# 时区配置（避免时间不对）
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/app.jar"]