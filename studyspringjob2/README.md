在 Spring Boot 中定时执行任务的例子2 - 使用 Quartz memory



创建 Spring Starter 项目
名称：studyspringjob2



在选择包的时候。

选择 Web 下面的 Spring Web
选择 I/O 下面的 Quartz Scheduler




创建示例 Job .
DemoJob01，DemoJob02
需要继承 QuartzJobBean.


创建 ScheduleConfiguration 类，配置作业。 





将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Quartz 的配置






在 studyspringjob2 目录下，运行
mvnw spring-boot:run



观察控制台，是否有定时输出的日志。


