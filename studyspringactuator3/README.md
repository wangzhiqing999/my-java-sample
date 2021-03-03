在 Spring Boot 监控端点 Actuator - info 端点


创建 Spring Starter 项目
名称：studyspringactuator3



在选择包的时候。

选择 Web 下面的 Spring Web
选择 Ops 下面的 Spring Boot Actuator



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Actuator 的配置


新增一个 DemoInfoContributor， 用于模拟  自定义的 信息 。

新增一个 ActuateConfig， 用于 配置 info 。


在 studyspringactuator3 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/actuator/info
{
    "app":{
        "java":{
            "source":"11.0.8",
            "target":"11.0.8"
        },
        "encoding":"UTF-8",
        "version":"0.0.1-SNAPSHOT"
    },
    "demo":{
        "key":"value"
    },
    "example":{
        "key":"value"
    },
    "example02":"nicai"
}


其中：
"app" 的部分，是定义在 application.yaml 的。
"demo" 的部分，是定义在 DemoInfoContributor 的。
"example" 与 "example02" 的部分，是定义在 ActuateConfig 的。

