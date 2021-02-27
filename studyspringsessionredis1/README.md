Spring  Session + Redis 学习的例子1


创建 Spring Starter 项目
名称：studyspringsessionredis1



在选择包的时候。

选择 Web 下面的 Spring.Web


修改 pom.xml 追加依赖



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写用于测试的 redis 配置信息.



添加 SessionConfiguration
来作为 session 的配置处理.



添加一个 SessionController 用于测试.





在 studyspringsessionredis1 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/session/hello
用于测试网站可用.


先访问
http://localhost:8080/session/get_all
测试从 Session 中获取数据. 
首次访问情况下，返回应该为 {}

访问
http://localhost:8080/session/set?key=name&value=ZhangSan
测试向 Session 中存储数据. 数据最终存储在 redis 中.

访问
http://localhost:8080/session/get_all
测试从 Session 中获取数据. 数据来源是在 redis 中.
预期返回的结果为 {"name":"ZhangSan"}

访问
http://localhost:8080/session/set?key=name2&value=LiSi
测试向 Session 中存储数据. 数据最终存储在 redis 中.

访问
http://localhost:8080/session/get_all
测试从 Session 中获取数据. 数据来源是在 redis 中.
预期返回的结果为 {"name":"ZhangSan","name2":"LiSi"}


可以通过重新启动网站，再次观察数据是否能获取。
以及通过 终端执行 redis-cli 命令，查询 redis 上是否存储了数据，来进行确认。


