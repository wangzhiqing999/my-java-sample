在 Spring Boot 中定时执行任务的例子3 - 使用 Quartz jdbc



创建 Spring Starter 项目
名称：studyspringjob3



在选择包的时候。

选择 Web 下面的 Spring Web
选择 I/O 下面的 Quartz Scheduler
选择 SQL 下面的 Spring Data Jdbc 与 MySQL Driver


创建示例 Job .
将 studyspringjob2 项目的 
DemoService，DemoJob01，DemoJob02
简单复制到本项目.


在 Job 类上面，增加 @DisallowConcurrentExecution 注解，
保证相同 JobDetail 在多个 JVM 进程中，有且仅有一个节点在执行。



创建 ScheduleConfiguration 类，配置作业。 


将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写  Quartz jdbc 调度任务的配置


前往
http://www.quartz-scheduler.org/downloads/
下载 Latest Stable Releases Downloads
quartz-2.3.0-distribution.tar.gz



解压缩后，在 src\org\quartz\impl\jdbcjobstore 目录下，找到 tables_mysql.sql 文件

连接到本机的测试 mysql 数据库
先执行
CREATE DATABASE `studyspringjob3` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `studyspringjob3`


执行 tables_mysql.sql 文件中的脚本.





在 studyspringjob3 目录下，运行
mvnw spring-boot:run



观察控制台，是否有定时输出的日志。

查询 MySQL 库里面的表。
表字段详情，参考
https://www.jianshu.com/p/b94ebb8780fa



