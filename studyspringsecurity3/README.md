Spring Security 学习的例子3 - 自定义 Spring Security 的配置 - 注解方式完成


创建 Spring Starter 项目
名称：studyspringsecurity3



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security




创建一个 SecurityConfig ， 继承 WebSecurityConfigurerAdapter 抽象类，实现 Spring Security 在 Web 场景下的自定义配置。





创建一个  DemoController
用于测试访问
预期的路径分别是
http://localhost:8080/demo/echo
http://localhost:8080/demo/home
http://localhost:8080/demo/admin
http://localhost:8080/demo/normal







在 studyspringsecurity3 目录下，运行
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


