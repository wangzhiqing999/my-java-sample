Spring Configuration 学习的例子 



创建 Spring Starter 项目
名称：studyspringconfiguration



在选择包的时候。

选择 Web 下面的 Spring Web
选择 Developer Tools 下面的 Spring Configuration Process






### 简单测试 基本的数据。


创建一个 TestConfigData 类，用于存储配置信息.
类头上需要增加
@Component
@ConfigurationProperties(prefix="com.wzq.configdata")



修改 application.properties
添加配置的数据.


添加一个 HelloWorldController
用于测试是否能够获取配置文件中的数据.





### 测试 开发/产品 的开关模式.

创建一个 EmailService 接口， 定义为 发生邮件的处理.

创建一个 DevEmailServiceImpl 类， 实现 EmailService 接口， 预期为【开发环境】下，不发送邮件。
注解为
@Service
@Profile("dev")

创建一个 ProdEmailServiceImpl 类， 实现 EmailService 接口， 预期为【生产环境】下，发送邮件。
注解为
@Service
@Profile("prod")



application.properties 文件中，配置好
spring.profiles.active=dev
也就是 【开发环境】


修改 HelloWorldController
增加调用 emailService.send() 的方法。






在 studyspringconfiguration 目录下，运行
mvnw spring-boot:run



测试

http://localhost:8080/hello
标准 Hello World.


http://localhost:8080/configName
http://localhost:8080/configTitle
测试配置的基本读取.


http://localhost:8080/configSummary
测试配置中使用随机数，参数间的引用.
观察控制台的输出。看看 emailService.send() 具体是执行了哪一个实现。



