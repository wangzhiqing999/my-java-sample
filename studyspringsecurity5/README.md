Spring Security 学习的例子5 - 基于 MySQL 数据库的身份认证和角色授权


创建 Spring Starter 项目
名称：studyspringsecurity5



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security
选择 SQL 下面的 Spring Data JPA 与 MySQL Driver



从 studyspringsecurity4 项目， 将
实体类（UserInfo）
Repository（UserInfoRepository）
Service（UserInfoService，UserInfoServiceImpl）
自定义UserDetailsService（CustomUserDetailService）
初始化测试账号类（DataInit）
安全配置类 SecurityConfig
用于测试的 DemoController

都复制过来




添加配置信息
在application.properties文件中添加配置信息：

spring.datasource.url = jdbc:mysql://localhost:3306/springsecurity
spring.datasource.username = root
spring.datasource.password = 123456
spring.datasource.driverClassName = com.mysql.jdbc.Driver

# Specify the DBMS  
spring.jpa.database = MYSQL
# Show or not log for each sql query  
spring.jpa.show-sql = true
# Hibernate ddl auto (create, create-drop, update)  
spring.jpa.hibernate.ddl-auto = create-drop




配置本机的 mysql 数据库
在 mysql 客户端下，简单执行下面的脚本，创建 springsecurity 库.
CREATE DATABASE `springsecurity` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;




在 studyspringsecurity5 目录下，运行
mvnw spring-boot:run



在 mysql 客户端下，查询数据
SELECT * FROM springsecurity.user_info;
能够看到，初始化测试账号类（DataInit） 所创建的2行数据。




访问
http://localhost:8080/demo/echo
无需登录，直接可访问


访问
http://localhost:8080/demo/home
需要登录之后，才可以访问

访问
http://localhost:8080/demo/admin
需要以 admin 登录之后，才可以访问
如果以 user 登录之后，则是错误页面

访问
http://localhost:8080/demo/normal
需要以 user 登录之后，才可以访问
如果以 admin 登录之后，则是错误页面




-------------------------------------------------------

前面的 在 mysql 客户端下，查询数据
SELECT * FROM springsecurity.user_info;
密码是以 明文的方式， 存储在数据库中的。

需要做加密的处理。


1.修改 DataInit.java
将原来的
admin.setPassword("123");
修改为
admin.setPassword(passwordEncoder.encode("123"));

也就是加密后，再存储到数据库中。

user.setPassword("123"); 
也是一样。


2.修改 CustomUserDetailService.java
将原来的
User userDetails = new User(userInfo.getUsername(),passwordEncoder.encode(userInfo.getPassword()),authorities);
修改为
User userDetails = new User(userInfo.getUsername(), userInfo.getPassword(), authorities);



3.在 studyspringsecurity5 目录下，运行
mvnw spring-boot:run


在 mysql 客户端下，查询数据
SELECT * FROM springsecurity.user_info;
能够看到，初始化测试账号类（DataInit） 所创建的2行数据。

观察数据， password 列是加密后的数据。



4. 访问4个页面，进行测试。


