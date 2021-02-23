Spring Security 学习的例子8 - 基于URL动态权限


创建 Spring Starter 项目
名称：studyspringsecurity8



在选择包的时候。

选择 Web 下面的 Spring.Web
选择 Security 下面的 Spring.Security
选择 SQL 下面的 Spring Data JPA 与 MySQL Driver
选择 Template Engines 下面的 Thymeleaf




从 studyspringsecurity7 项目， 将
实体类（UserInfo, Role）
Repository（UserInfoRepository, RoleRepository）
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

1.1 定义权限实体类
创建实体类，用于存储权限的信息 Permission



1.2 权限持久化
创建PermissionReporitory 持久化操作



1.3 修改DataInit
创建角色后，需要针对角色，设置这个角色所能访问的 URL 地址.


1.4 加载权限信息
添加 PermissionService 与 PermissionServiceImpl



2.1 加载权限信息
添加 MyFilterInvocationSecurityMetadataSource 

2.2 权限校验
添加 MyAccessDecisionManager  

2.3
修改 SecurityConfig



2.4
注释掉 HomeController 的 helloAdmin ， helloUser 方法上面的 @PreAuthorize
增加一个 helloWorld 用于模拟 “忘记配置权限” 的情况。




3. 运行测试

在 studyspringsecurity8 目录下，运行
mvnw spring-boot:run




在 mysql 客户端下，查询数据

-- 查询每一个路径，都有什么角色的什么用户， 能够进行访问。
SELECT 
	permission.name,
    permission.url,
    role.name AS role_name,
    user_info.username
FROM 
  permission 
	JOIN role_permission ON (permission.id = role_permission.permission_id)
    JOIN role ON (role.rid = role_permission.role_id)
    JOIN user_role ON(role.rid = user_role.role_id)
    JOIN user_info ON(user_info.uid = user_role.uid)
;






测试
http://localhost:8080/

以 admin 登录， 将显示  admin page 的链接， 与 user page 的链接。
以 user 登录， 将只显示 user page 的链接， 强行输入 http://localhost:8080/helloAdmin 访问管理员页面，将跳转至 403 错误页面。


由于代码中，当发生 “权限表中没有的路径”时，默认的设置是 “要求管理员角色”。
那么这种情况下。
以 admin 登录，可以访问   http://localhost:8080/helloWorld
以 user 登录，访问   http://localhost:8080/helloWorld 将跳转至 403 错误页面。

