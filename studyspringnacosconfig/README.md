Spring Boot + Nacos 学习的例子 - config


开发之前，需要先搭建一个 nacos-server。
参考操作：https://nacos.io/zh-cn/docs/quick-start.html
本例子的测试 nacos-server 运行在局域网内的 http://192.168.1.39:8848/nacos/ 下。


先去 nacos-server 
http://192.168.1.39:8848/nacos/ 
配置管理-->配置列表下
点 [+] 创建一个用于演示的配置

data-id为 example
配置格式为 YAML
配置内容为
order:
  pay-timeout-seconds: 120 # 订单支付超时时长，单位：秒。
  create-frequency-seconds: 10 # 订单创建频率，单位：秒






创建 Spring Starter 项目
名称：studyspringnacosconfig



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


创建一个 OrderProperties.java 用于存储配置数据.


修改 StudyspringnacosconfigApplication
增加两个 CommandLineRunner


创建一个 DemoController
用于简单返回 OrderProperties 的数值。


在 studyspringnacosconfig 目录下，运行
mvnw spring-boot:run


观察控制台输出
能正常看到两个 CommandLineRunner 输出的日志，获取 配置的数值。


访问 http://localhost:8080/demo
返回
{"payTimeoutSeconds":120,"createFrequencySeconds":10}





