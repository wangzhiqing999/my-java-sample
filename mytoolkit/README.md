# mytoolkit



## 用途
一个 java 的 Web Api 项目.

当项目涉及调用其它公司的服务时。
其它公司，一般是提供接口文档，以及 java SDK。

在时间紧张的情况下，根据接口文档，硬写 C# 代码，来不及的情况下。
采取，对这个 Java SDK， 外面套一个 Web Api 的壳。
然后，C# 或者 Html 页面，直接调用这个 Web Api 进行处理。



## 空壳的搭建步骤.

https://start.spring.io/

选择
Project = Maven Project
Language = Java
Spring Boot = 2.3.2
......
Packaging = War
Java = 8

Dependencies = Spring Web

点击 【Generate】后，下载打包后的 zip 文件，解压缩.




## Java 环境设置.

环境变量中，设置：
JAVA_HOME
D:\java_dev_env\jdk1.8.0_151 【此为测试机器的路径】

PATH
追加
D:\java_dev_env\jdk1.8.0_151\bin  【此为测试机器的路径】





## maven 环境设置

环境变量中，设置：
MAVEN_HOME
D:\java_dev_env\apache-maven-3.5.4 【此为测试机器的路径】

PATH
追加
D:\java_dev_env\apache-maven-3.5.4\bin  【此为测试机器的路径】





## 编译打包空白项目.

在当前目录下， 运行
mvn clean package

正常的话，表示环境配置没有问题了。




## 项目追加 Swagger  支持.

修改 pom.xml，  追加 dependency 的配置.

增加一个 SwaggerConfig.java




## 新增一个 DemoController



## 编译打包/测试运行


打包
mvn clean package


测试运行.
mvnw spring-boot:run


验证结果
http://localhost:8080/swagger-ui.html


