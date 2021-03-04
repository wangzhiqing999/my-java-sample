Spring Boot + Nacos 学习的例子 -  配置监听器


开发之前，需要先搭建一个 nacos-server。
参考操作：https://nacos.io/zh-cn/docs/quick-start.html
本例子的测试 nacos-server 运行在局域网内的 http://192.168.1.39:8848/nacos/ 下。


先去 nacos-server 
http://192.168.1.39:8848/nacos/ 
配置管理-->配置列表下--> public 下
点 [+] 创建一个用于演示的配置

data-id为 example-auto-refresh
数值为：
logging:
  level:
    cn:
      wzq:
        studyspringnacos:
          controller:
            DemoController: INFO




创建 Spring Starter 项目
名称：studyspringnacosconfig3

在选择包的时候。

选择 Web 下面的 Spring.Web


修改 pom.xml
追加:
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-config-spring-boot-starter</artifactId>
    <version>0.2.7</version>
</dependency>



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写用于测试的 nacos 配置信息.
相当于从 studyspringnacosconfig2 项目，复制过来，然后修改一行
data-id: example-auto-refresh # 使用的 Nacos 配置集的 dataId。



创建一个 LoggingSystemConfigListener ， 用于 监听 logging.level 配置项的变更，修改 Logger 的日志级别。



创建一个 DemoController
两个方法：/logger 与 /info
分别做日志的输出.



在 studyspringnacosconfig3 目录下，运行
mvnw spring-boot:run


访问 http://localhost:8080/logger
访问 http://localhost:8080/info
观察日志.

看到只有 [info][测试一下] 的日志。


去 nacos-server 
http://192.168.1.39:8848/nacos/ 
修改配置内容为
logging:
  level:
    cn:
      wzq:
        studyspringnacos:
          controller:
            DemoController: DEBUG



再次访问
http://localhost:8080/logger
http://localhost:8080/info
观察日志.

看到有
[logger][测试一下]
[info][测试一下]
的日志。

