在 Spring Boot 中定时执行任务的例子1 - 使用 Spring Task 



创建 Spring Starter 项目
名称：studyspringjob1



在选择包的时候。

选择 Web 下面的 Spring Web



创建 ScheduleConfiguration 类，配置 Spring Task 。 

创建 创建 DemoJob 类，作为一个定时任务类。




将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Spring Task 调度任务的配置






在 studyspringjob1 目录下，运行
mvnw spring-boot:run



观察控制台，是否有定时输出的日志。


