Spring Security 学习的例子6 - 自定义登录页面和构建主页


创建 Spring Starter 项目
名称：studyspringsecurity6



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security
选择 SQL 下面的 Spring Data JPA 与 MySQL Driver
选择 Template Engines 下面的 Thymeleaf




从 studyspringsecurity5 项目， 将
实体类（UserInfo）
Repository（UserInfoRepository）
Service（UserInfoService，UserInfoServiceImpl）
自定义UserDetailsService（CustomUserDetailService）
初始化测试账号类（DataInit）
安全配置类 SecurityConfig
配置信息文件 application.properties

都复制过来





修改 SecurityConfig， 变更一些配置信息.




创建 HomeController.java
添加基本的 登录页，首页， 管理员页面， 普通用户页面。

分别为：
/login
/ 或 /index
/helloAdmin
/helloUser




在 templates下编写相应的 html 页面.




在 templates/error 下编写相应的 403.html 页面.





在 studyspringsecurity6 目录下，运行
mvnw spring-boot:run



测试
http://localhost:8080/

以 admin 登录， 将显示  admin page 的链接， 与 user page 的链接。
以 user 登录， 将只显示 user page 的链接， 强行输入 http://localhost:8080/helloAdmin 访问管理员页面，将跳转至 403 错误页面。

