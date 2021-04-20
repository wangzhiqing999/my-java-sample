Docker 部署 SpringBoot 项目的例子 


创建 Spring Starter 项目
名称：springdocker

Packaging 设置为 Jar


在选择包的时候。
选择 Web 下面的 Spring Web


创建一个最简单的 HelloWorldController



在 src/main/resources 下面创建 logback-spring.xml 文件
配置好，开发/测试环境，日志级别为 INFO
生产环境，日志级别为 ERROR


application.properties 文件中，配置好

logging.file.name=./log/springboot.log
也就是日志文件名.

spring.profiles.active=dev
也就是 【开发环境】




在 springdocker 目录下，运行
mvnw spring-boot:run

先本地测试
http://localhost:8080/api/hello

http://localhost:8080/api/testDebug
http://localhost:8080/api/testInfo
http://localhost:8080/api/testWarn
http://localhost:8080/api/testError


观察输出，以及日志文件。






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
studyspringdocker   latest              7a1b5913c459        8 seconds ago       122MB
openjdk             8-jdk-alpine        a3562aa0b991        23 months ago       105MB




创建本地的日志目录
mkdir log


启动容器
sudo docker run -itd --mount type=bind,source=$(pwd)/log,target=/log -p 8088:8080 studyspringdocker


参数说明：


--mount 由多个名值对组成，逗号分隔，每个键值由 = 元组组成。–mount 语法比 -v 或 --volume更冗长，但键的顺序并不重要，并且标志的值更易于理解
要挂载的类型 type，可以是 bind、volume 或 tmpfs。
要挂载的源 source，对于有名字的卷，这里是卷的名字。对于匿名卷忽略这个字段。可以指定为 src 或 source。
要挂载的目的地 destination，将文件或目录挂载在容器中的路径作为其值。 可能被指定为 destination、dst 或 target。

上面的例子，是把当前目录下的 /log 目录，挂载到 容器的 /log 目录.



-p 是做端口映射，格式为：主机(宿主)端口:容器端口， 也就是 服务器的端口，与容器的端口进行映射。

上面的例子，是将服务器中的8088端口映射到容器中的8080(项目中端口配置的是8080)端口



测试访问
http://192.168.1.39:8088/api/hello
http://192.168.1.39:8088/api/testDebug
http://192.168.1.39:8088/api/testInfo
http://192.168.1.39:8088/api/testWarn
http://192.168.1.39:8088/api/testError



查询运行的容器.
sudo docker ps

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
801385769fc3        studyspringdocker   "java -jar /app.jar"   53 seconds ago      Up 50 seconds       0.0.0.0:8088->8080/tcp   suspicious_lovelace



查看容器的控制台的日志.

sudo docker logs -f --tail=100 801385769fc3



停止容器
sudo docker stop 801385769fc3


删除容器
sudo docker rm 801385769fc3


删除镜像
sudo docker rmi  7a1b5913c459




查看 springboot 日志文件.
~/springdocker$ cd log
~/springdocker/log$ ls
springboot.log
~/springdocker/log$ more springboot.log









## 2021年4月20日追加使用 IntelliJ IDEA 部署 springboot 项目到 docker 的操作

不新写代码，沿用已有的项目结构。


配置测试服务器 192.168.1.39 ，允许 Docker 远程连接.

使用 IntelliJ IDEA 2020.3.3 (Community Edition) 打开本目录。

先安装 Docker 插件
在File-》Setting-》Plugins-》Browse Repositones 进行下载安装：

使用docker插件连接到Linux上的docker容器
在File-》Settings-》Build, Execution, Deployment-》Docker里用Linux主机的IP:2375进行连接：
连接成功后可以看到docker里所有的镜像以及容器



### 修改 pom.xml

追加使用docker-maven-plugin插件的定义.


### 运行 Maven package

在右边的 Maven 树形列表中，选择 springdocker -》 Lifecycle -》package 
鼠标右键，【Run Maven Build】



### 查询镜像

在底部的【Service】选项卡中，查看 Docker 的 Images ，能够看到 新创建的 镜像， springdocker:latest


### 创建容器.

选择上一步的镜像，鼠标右键，【Create container】 新建容器。

当前例子，主要需要输入
Container name: springdocker
Bind ports: 8088:8080

然后点击【Run】


### 测试访问
http://192.168.1.39:8088/api/hello


