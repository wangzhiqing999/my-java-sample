Spring Security 学习的例子1 - Hello World 级别的


创建 Spring Starter 项目
名称：studyspringsecurity1



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security





将 src\main\resources 目录下的 application.properties 修改为 application.yml
填写用于测试的 Spring Security 配置信息.


在 spring.security 配置项，设置 Spring Security 的配置，对应 SecurityProperties 配置类。
默认情况下，Spring Boot UserDetailsServiceAutoConfiguration 自动化配置类，会创建一个内存级别的 InMemoryUserDetailsManager Bean 对象，提供认证的用户信息。
这里，我们添加了 spring.security.user 配置项，UserDetailsServiceAutoConfiguration 会基于配置的信息创建一个用户 User 在内存中。
如果，我们未添加 spring.security.user 配置项，UserDetailsServiceAutoConfiguration 会自动创建一个用户名为 "user" ，密码为 UUID 随机的用户 User 在内存中。




创建一个  AdminController
用于测试访问
预期的路径是
http://localhost:8080/admin/demo
用于测试未登录时，会被拦截到登录界面。





在 studyspringsecurity1 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/admin/demo





自动跳转至
http://localhost:8080/login
这个登录页面


因为我们没有自定义登录界面，所以默认会使用 DefaultLoginPageGeneratingFilter 类，生成这个界面。





输入填写在 application.yml 里面的用户名与密码，登入后。
自动跳转至 
http://localhost:8080/admin/demo


显示填写在 AdminController 控制器中的文本。
