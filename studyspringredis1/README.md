Spring + redis 学习的例子1 - Hello World 级别的


创建 Spring Starter 项目
名称：studyspringredis1



在选择包的时候。

选择 Web 下面的 Spring.Web


修改 pom.xml 追加依赖



将 src\main\resources 目录下的 application.properties 修改为 application.yml
填写用于测试的 redis 配置信息.



添加 RedisConfig.java
来作为 redis 的配置处理.



添加一个 HelloController 用于测试.





在 studyspringredis1 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/hello
用于测试网站可用.


访问
http://localhost:8080/testSet?key=name&value=ZhangSan
测试向 redis 设置数据.


访问
http://localhost:8080/testGet?key=name
测试从 redis 获取数据.





访问
http://localhost:8080/getUser?id=1
测试从 redis 获取数据.
可以多按 F5 刷新几次， 同时观察后台的日志，那个 查询用户【id】=1 的，只出现一次。

访问
http://localhost:8080/saveOrUpdate?id=5&name=WangWu
http://localhost:8080/getUser?id=5
http://localhost:8080/saveOrUpdate?id=5&name=WangWu555
http://localhost:8080/getUser?id=5
http://localhost:8080/deleteUser?id=5
http://localhost:8080/getUser?id=5
测试 新增，查询，修改，查询，删除，查询。
执行时，注意观察后台的日志。
