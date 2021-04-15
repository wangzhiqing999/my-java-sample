Docker 部署 SpringBoot 项目的例子 


创建 Spring Starter 项目
名称：springdocker

Packaging 设置为 Jar


在选择包的时候。
选择 Web 下面的 Spring Web


创建一个最简单的 HelloWorldController


在 springdocker 目录下，运行
mvnw spring-boot:run

先本地测试
http://localhost:8080/api/hello






代码从本机，复制到 用于测试的 Linux 机器上。
测试机器的 ip 为 192.168.1.39


运行
mvn package
打包，准备发布.



编写 Dockerfile 文件



制作镜像.
sudo docker build -t studyspringdocker .




查看镜像
sudo docker images

REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
studyspringdocker   latest              f8ceb4379ce4        5 seconds ago       122MB
openjdk             8-jdk-alpine        a3562aa0b991        23 months ago       105MB



启动容器
sudo docker run -d -p 8088:8080 studyspringdocker

-d 参数是让容器后台运行 
-p 是做端口映射，此时将服务器中的8088端口映射到容器中的8080(项目中端口配置的是8080)端口



测试访问
http://192.168.1.39:8088/api/hello



查询运行的容器.
sudo docker ps

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
69b7c53d9383        studyspringdocker   "java -Djava.securit…"   2 minutes ago       Up 2 minutes        0.0.0.0:8088->8080/tcp   cool_bohr



停止容器
sudo docker stop 69b7c53d9383

