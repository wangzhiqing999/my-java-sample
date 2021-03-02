在 Spring Boot 中异步执行任务的例子1 - 使用 Spring Task



创建 Spring Starter 项目
名称：studyspringasyncjob1



在选择包的时候。

选择 Web 下面的 Spring Web



修改 Studyspringasyncjob1Application
增加 @EnableAsync



创建 GlobalAsyncExceptionHandler类 与 AsyncConfig 类，配置 异步时异常处理。 




创建 DemoService 与 DemoAsyncService 
分别演示 同步 与 异步 的任务.



创建一个 HelloWorldController 用于测试.



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Spring Task 调度任务的配置






在 studyspringasyncjob1 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/hello
确认网站正常启动


访问
http://localhost:8080/demo
同步执行
观察控制台日志
[demo][开始执行]
[execute01]
[execute02]
[demo][结束执行，消耗时长 15003 毫秒]


访问
http://localhost:8080/demoasync
异步执行（不等待执行结果）
观察控制台日志
[demoasync][开始执 行]
[demoasync][结束执 行，消耗时长 5 毫秒]
[execute02]
[execute01]


访问
http://localhost:8080/demoAsyncWithFuture
异步执行（等待执行结果）
观察控制台日志
[demoAsyncWithFuture][开始执行]
[execute01]
[execute02]
[demoAsyncWithFuture][结束执行，消耗时长 10002 毫秒]

