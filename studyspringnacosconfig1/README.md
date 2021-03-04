Spring Boot + Nacos 学习的例子 - 多环境配置


开发之前，需要先搭建一个 nacos-server。
参考操作：https://nacos.io/zh-cn/docs/quick-start.html
本例子的测试 nacos-server 运行在局域网内的 http://192.168.1.39:8848/nacos/ 下。


先去 nacos-server 
http://192.168.1.39:8848/nacos/ 
命名空间下
新建命名空间1
命名空间名=dev
描述=开发环境的命名空间

新建命名空间2
命名空间名=prod
描述=产品环境的命名空间

创建完毕后，记录下 命名空间 对应的 命名空间ID
本次测试操作的结果如下：
dev：0054342d-8449-4d6e-89f6-a33f983c73d9
prod：378a804a-eba3-4f35-bd1d-eaf1fa68bf69



配置管理-->配置列表下
在顶部的 public | dev | prod 选择卡中。
分别在 dev 与 prod 下，分别 点 [+] 创建一个用于演示的配置

dev 环境下
data-id为 example
配置格式为 YAML
配置内容为
order:
  pay-timeout-seconds: 12000 # 订单支付超时时长，单位：秒。
  create-frequency-seconds: 1000 # 订单创建频率，单位：秒


prod 环境下
data-id为 example
配置格式为 YAML
配置内容为
order:
  pay-timeout-seconds: 1200 # 订单支付超时时长，单位：秒。
  create-frequency-seconds: 100 # 订单创建频率，单位：秒











创建 Spring Starter 项目
名称：studyspringnacosconfig1



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

新增 application-dev.yaml 用于存储开发环境下的配置.
新增 application-prod.yaml 用于存储产品环境下的配置.



从 studyspringnacosconfig 项目，复制 OrderProperties 与 DemoController 到本项目。


修改 application.yaml
spring.profiles.active=dev 的情况下

在 studyspringnacosconfig1 目录下，运行
mvnw spring-boot:run
访问 http://localhost:8080/demo
返回
{"payTimeoutSeconds":12000,"createFrequencySeconds":1000}



修改 application.yaml
spring.profiles.active=prod 的情况下

在 studyspringnacosconfig1 目录下，运行
mvnw spring-boot:run
访问 http://localhost:8080/demo
返回
{"payTimeoutSeconds":1200,"createFrequencySeconds":100}



