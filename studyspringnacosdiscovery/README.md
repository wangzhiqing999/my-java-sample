Spring Boot + Nacos 学习的例子 - discovery


开发之前，需要先搭建一个 nacos-server。
参考操作：https://nacos.io/zh-cn/docs/quick-start.html
本例子的测试 nacos-server 运行在局域网内的 http://192.168.1.39:8848/nacos/ 下。


创建 Spring Starter 项目
名称：studyspringnacosdiscovery



在选择包的时候。

选择 Web 下面的 Spring.Web


修改 pom.xml
追加:
<dependency>
	<groupId>com.alibaba.boot</groupId>
	<artifactId>nacos-discovery-spring-boot-starter</artifactId>
	<version>0.2.7</version>
</dependency>



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写用于测试的 nacos 配置信息.




创建一个 ProviderController
用于模拟一个提供服务的。

创建一个 ConsumerController
用于模拟一个使用服务的。
Java端，向 nacos-server 获取 提供服务的机器信息， 然后去调用实际的服务.

创建一个 DiscoveryController
传入 服务名， 返回 服务器列表.


在 studyspringnacosdiscovery 目录下，运行
mvnw spring-boot:run


先去 nacos-server 
http://192.168.1.39:8848/nacos/ 
服务管理-->服务列表下
查看，是否有新增的服务。



测试 直接访问提供服务的.
http://localhost:8080/provider/demo


测试 间接访问提供服务的.
http://localhost:8080/consumer/demo


测试 获取服务列表.
http://localhost:8080/discovery/get?serviceName=demo-application

