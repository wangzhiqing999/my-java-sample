学习使用 MyBatis-Plus 的例子1


创建 Spring Starter 项目
名称：studymybatisplus1



在选择包的时候。
选择 Web 下面的 Spring Web


修改 pom.xml 添加依赖


src\main\resources\db 目录下，准备好
schema-h2.sql
与
data-h2.sql
也就是建表的 sql 与 测试数据的 sql



src\main\resources 目录下，修改 application.yml 文件内容



cn.wzq.studymybatisplus.entity 下面，创建一个 User 类

cn.wzq.studymybatisplus.mapper 下面，创建一个 UserMapper 类

cn.wzq.studymybatisplus.controller 下面，创建一个 UserController 类


Studymybatisplus1Application 类头上，增加 @MapperScan




在 studymybatisplus1 目录下，运行
mvnw spring-boot:run



测试

http://localhost:8080/users/hello
标准 Hello World.


http://localhost:8080/users/list
查看全部.

http://localhost:8080/users/get?id=2
查看单个

