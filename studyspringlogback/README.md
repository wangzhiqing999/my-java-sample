在Spring Boot 中记录日志的例子 



创建 Spring Starter 项目
名称：studyspringlogback



在选择包的时候。

选择 Web 下面的 Spring Web






在 src/main/resources 下面创建 logback-spring.xml 文件
配置好，开发/测试环境，日志级别为 INFO
生产环境，日志级别为 ERROR


application.properties 文件中，配置好

logging.file.name=./springboot.log
也就是日志文件名.

spring.profiles.active=dev
也就是 【开发环境】




创建 HelloWorldController
增加调用 
logger.debug
logger.info
logger.warn
logger.error
的方法.





在 studyspringlogback 目录下，运行
mvnw spring-boot:run



测试

http://localhost:8080/hello
标准 Hello World.


http://localhost:8080/testDebug
http://localhost:8080/testInfo
http://localhost:8080/testWarn
http://localhost:8080/testError


然后查看日志.
在 springboot.log 文件中。



修改 application.properties 文件中，配置
spring.profiles.active=prod
也就是 【生产环境】
然后再运行测试一次。