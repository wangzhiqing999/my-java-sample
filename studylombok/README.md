lombok 使用的学习



开发工具是 Spring Tool Suite
需要做一定的配置工作。

1、下载lombok.jar
lombok.jar官方下载地址：https://projectlombok.org/download

下载后，将文件 lombok.jar
复制到 D:\java_dev_env 目录下


2.安装
D:\java_dev_env>java -jar lombok.jar

运行过程中，首先搜索当前计算机的 IDE
如果搜索不到，则提示，需要自己指定。

由于本机的开发环境，是复制粘贴的，搜索不到。
点击【Specify location..】来手工指定 Eclipse安装目录
选择本机的  D:\java_dev_env\sts-3.9.7.RELEASE  目录
选择后
会自动识别  D:\java_dev_env\sts-3.9.7.RELEASE\STS.exe

点击【Install / Update】
点击【Quit Installer】












## 创建 Maven 项目
选择 “maven-archetype-quickstart”
项目名称为 studylombok


### 测试基本的 @Data

修改 pom.xml

追加依赖
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.18</version>
      <scope>provided</scope>
    </dependency>


创建测试类

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long id;
	private String userName;
	private String password;
	private String name;
	private Integer age;
	private String email;
}


修改运行类

public class App
{
    public static void main( String[] args )
    {
        User user = new User();
        user.setUserName("ZhangSan");
        System.out.println(user.getUserName());
    }
}


测试运行



### 测试 @Builder

User 类上面， 简单加  @Builder

User 类 password 的上面， 简单添加  @Builder.Default
给 password 设置一个初始值.


然后，App类，测试 builder 的使用。

	User user2 = User.builder()
			.id(123L)
			.name("张三")
			.userName("zhang3")
			.age(25)
			.email("zhangsan@test.com")
			.build();






### 测试 @ToString

User 类上面， 简单加  @ToString

然后，App类， 尝试 System.out.println(user2); 的处理







### 测试 @Slf4j

修改 pom.xml

追加依赖
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.26</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.26</version>
    </dependency>

追加一个 log4j.properties

log4j.rootLogger = debug, console
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern = [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} method:%l%m%n

修改 运行类

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App
{
    public static void main( String[] args )
    {

        log.debug("Test Debug");
        log.info("Test Info");
        log.warn("Test Warn");
        log.error("Test Error");


        User user = new User();
        user.setUserName("ZhangSan");

        System.out.println(user.getUserName());
    }
}


测试运行



