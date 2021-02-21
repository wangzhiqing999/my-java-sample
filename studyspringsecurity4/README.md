Spring Security 学习的例子4 - 基于内存数据库的身份认证和角色授权


创建 Spring Starter 项目
名称：studyspringsecurity4



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security





1.1 添加依赖
在 pom.xml 添加 SpringData JPA 和 hsqldb 的依赖

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
	<groupId>org.hsqldb</groupId>
	<artifactId>hsqldb</artifactId>
	<scope>runtime</scope>
</dependency>





1.2 创建实体类
创建UserInfo实体类


1.3 创建Repository
创建和数据库交互的UserInfoRepository


1.4 创建Service
创建UserInfoService
创建UserInfoService的实现类


1.5 自定义UserDetailsService
自定义一个UserDetailsService，取名为CustomUserDetailService，该类需要实现接口UserDetailsService，主要是实现loadUserByUsername方法


1.6 初始化测试账号
这里使用一个DataInit类，初始化两个账号admin/123和user/123





创建一个 SecurityConfig ， 继承 WebSecurityConfigurerAdapter 抽象类，实现 Spring Security 在 Web 场景下的自定义配置。






创建一个  DemoController
用于测试访问
预期的路径分别是
http://localhost:8080/demo/echo
http://localhost:8080/demo/home
http://localhost:8080/demo/admin
http://localhost:8080/demo/normal







在 studyspringsecurity4 目录下，运行
mvnw spring-boot:run



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


