kaptcha 验证码简单实例


创建 Spring Starter 项目
名称：studykaptcha1



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Template Engines 下面的 Thymeleaf




修改 pom.xml
添加
  <!--验证码kaptcha-->
  <dependency>
      <groupId>com.github.penggle</groupId>
      <artifactId>kaptcha</artifactId>
      <version>2.3.2</version>
  </dependency>
  
  
添加一个 CaptchaConfig 用于基本的设置.



添加一个 TestController 用于 显示验证码， 验证验证码， 以及测试的页面

添加用于测试的页面 index.html







在 studykaptcha1 目录下，运行
mvnw spring-boot:run



测试
http://localhost:8080/


输入正确的验证码，提交。
输入错误的验证码，提交。

