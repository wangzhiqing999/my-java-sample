在 Spring Boot 中定时执行任务的例子4 - 使用 XXL-JOB


注意：
XXL-JOB 是划分为 调度中心 与 执行器 两个部分的。

需要先去  https://gitee.com/xuxueli0323/xxl-job  克隆代码下来。
xxl-job-admin 是调度中心的代码。

本项目是一个 XXL-JOB 的执行器。

两者需要配合起来运行。



创建 Spring Starter 项目
名称：studyspringjob4



在选择包的时候。
选择 Web 下面的 Spring Web

修改 pom.xml 追加依赖.



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
添加 XXL-JOB 的配置

注意：
xxl.job.executor.appname: studyspringjob4
这个名字，后面是需要在管理页填写的。


添加 XxlJobConfiguration 用于配置.



添加 DemoJob 用于演示一个作业.
需要注意，这个类上面的 @JobHandler("demoJob")
后面是需要在管理页填写的。




在 studyspringjob4 目录下，运行
mvnw spring-boot:run

此时观察控制台，应该没有定时输出的日志。



前往 xxl-job-admin 是调度中心，添加一个 执行器.
http://127.0.0.1:8080/xxl-job-admin/jobgroup

新建执行器的时候， AppName 填写 studyspringjob4
也就是 定义在 application.yaml 里面的 xxl.job.executor.appname

添加后，连接成功的情况下，在执行器后面，会有 【OnLine 机器地址】的显示.



前往任务管理.
http://127.0.0.1:8080/xxl-job-admin/jobinfo

新建一个任务
执行器 = 选择刚才创建的执行器 
路由策略 = 轮询
Cron为每分钟执行一次  = 0****?
运行模式 = BEAN
JobHandler demoJob
阻塞处理策略 = 单机串行


保存完毕后，在列表页面，【操作】选择【执行一次】

在本项目的控制台，观察输出日志。
是否有 DemoJob 的相关输出信息。

然后，在列表页面，【操作】选择【查询日志】

然后，在在列表页面，【操作】选择【执行】
接着，在列表页面，【操作】选择【查询日志】




注意事项
xxl-job-admin 调度中心的版本，需要和执行器的版本一致。
当前这个例子

调度中心，是运行
java -jar xxl-job-admin-2.1.1.jar

执行器，是
<dependency>
	<groupId>com.xuxueli</groupId>
	<artifactId>xxl-job-core</artifactId>
	<version>2.1.1</version>
</dependency>



如果调度中心的版本，与执行器的版本不一致。
可能会发生各种问题！

