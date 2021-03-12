Spring Boot + JPA 学习的例子 



创建 Spring Starter 项目
名称：studyspringjpa1



在选择包的时候。
选择 Web 下面的 Spring Web
选择 SQL 下面的 Spring Data JPA 与 MySQL Driver


修改 pom.xml 
添加依赖.



先添加最基础的 实体类
在 package cn.wzq.studyspringjpa.model.po  下面。


添加 DTO 对象， 用于存储 表关联后的数据。
在 package cn.wzq.studyspringjpa.model.dto  下面。


再添加 Repository 接口
在 package cn.wzq.studyspringjpa.repository 下面。



修改 application.properties



添加用于测试的 HelloController 与 UserController



前往 MySQL 客户端， 创建好数据库。



在 studyspringjpa1 目录下，运行
mvnw spring-boot:run



前往 MySQL 客户端，观察 表 是否已经创建。

尝试插入 测试的数据。



测试
http://localhost:8080/hello
观察 网站是否正常启动



测试 根据 id 查找
http://localhost:8080/user/findById?id=1
http://localhost:8080/user/findById?id=99999


测试 根据 名称 查找
http://localhost:8080/user/findByName?name=ZhangSan
http://localhost:8080/user/findByName?name=ZhangSan2



测试 自定义查询： 根据 id 查找名称
http://localhost:8080/user/findPersonNameById?id=1
http://localhost:8080/user/findPersonNameById?id=99999


测试 自定义查询： 根据 id 更新Person name
更新前.
http://localhost:8080/user/findPersonNameById?id=4
更新.
http://localhost:8080/user/updatePersonNameById?id=4&name=TEST123
更新后.
http://localhost:8080/user/findPersonNameById?id=4



测试 表关联查询
http://localhost:8080/user/getUserInformation?id=1



测试翻页.
http://localhost:8080/user/getUserInformationList?pageIndex=0&pageSize=3
http://localhost:8080/user/getUserInformationList?pageIndex=1&pageSize=3


http://localhost:8080/user/getUserInformationList?pageIndex=0&pageSize=4
http://localhost:8080/user/getUserInformationList?pageIndex=1&pageSize=4







具体的 JPA 文档，前往 
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
查看

