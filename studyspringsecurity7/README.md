Spring Security 学习的例子7 - 动态加载角色


创建 Spring Starter 项目
名称：studyspringsecurity7



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security
选择 SQL 下面的 Spring Data JPA 与 MySQL Driver
选择 Template Engines 下面的 Thymeleaf




从 studyspringsecurity6 项目， 将
实体类（UserInfo）
Repository（UserInfoRepository）
Service（UserInfoService，UserInfoServiceImpl）
自定义UserDetailsService（CustomUserDetailService）
初始化测试账号类（DataInit）
安全配置类 SecurityConfig
配置信息文件 application.properties
安全配置类 SecurityConfig

控制器 HomeController
templates下相应的 html 页面.

都复制过来






修改的部分

1.1 定义角色实体类
定义一个角色实体类Role


1.2 角色持久化
创建RoleRepository持久化操作


1.3 修改UserInfo的用户角色
将之前的 枚举的代码，变更为 多对多引用.


1.4 修改CustomUserDetailService
将之前的 枚举的代码，变更为 多对多引用.


1.5 修改DataInit



1.6 运行测试



在 studyspringsecurity7 目录下，运行
mvnw spring-boot:run




在 mysql 客户端下，查询数据

-- 角色数据.
SELECT * FROM springsecurity.role;
-- 用户数据.
SELECT * FROM springsecurity.user_info;
-- 用户-角色 多对多中间表.
SELECT * FROM springsecurity.user_role;



测试
http://localhost:8080/

以 admin 登录， 将显示  admin page 的链接， 与 user page 的链接。
以 user 登录， 将只显示 user page 的链接， 强行输入 http://localhost:8080/helloAdmin 访问管理员页面，将跳转至 403 错误页面。

