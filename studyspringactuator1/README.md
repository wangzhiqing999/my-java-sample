在 Spring Boot 监控端点 Actuator - 初始化


创建 Spring Starter 项目
名称：studyspringactuator1



在选择包的时候。

选择 Web 下面的 Spring Web
选择 Ops 下面的 Spring Boot Actuator



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Actuator 的配置


在 studyspringactuator1 目录下，运行
mvnw spring-boot:run


分别访问
http://localhost:8080/actuator/health

http://localhost:8080/actuator/beans

简单测试.




内置的可用的端点，参考：
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints



http://localhost:8080/actuator/beans
描述应用程序上下文里面全部的 Bean，以及它们的关系。

http://localhost:8080/actuator/env
获取全部环境属性。

http://localhost:8080/actuator/health
报告应用程序健康信息。

http://localhost:8080/actuator/info
获取应用程序定制信息。（初始情况下，是空白）

http://localhost:8080/actuator/mappings
描述全部的 URI 路径，以及它们和控制器的映射关系。

http://localhost:8080/actuator/metrics
这个是返回一个 名称列表。
{
    "names":[
        "http.server.requests",
        "jvm.buffer.count",
        "jvm.buffer.memory.used",
        "jvm.buffer.total.capacity",
        "jvm.classes.loaded",
        "jvm.classes.unloaded",
        "jvm.gc.live.data.size",
        "jvm.gc.max.data.size",
        "jvm.gc.memory.allocated",
        "jvm.gc.memory.promoted",
        "jvm.gc.pause",
        "jvm.memory.committed",
        "jvm.memory.max",
        "jvm.memory.used",
        "jvm.threads.daemon",
        "jvm.threads.live",
        "jvm.threads.peak",
        "jvm.threads.states",
        "logback.events",
        "process.cpu.usage",
        "process.start.time",
        "process.uptime",
        "system.cpu.count",
        "system.cpu.usage",
        "tomcat.sessions.active.current",
        "tomcat.sessions.active.max",
        "tomcat.sessions.alive.max",
        "tomcat.sessions.created",
        "tomcat.sessions.expired",
        "tomcat.sessions.rejected"
    ]
}


http://localhost:8080/actuator/metrics/system.cpu.usage
这个是从上面的名称列表中，选择一个，查询明细。
{
    "name":"system.cpu.usage",
    "description":"The \"recent cpu usage\" for the whole system",
    "baseUnit":null,
    "measurements":[
        {
            "statistic":"VALUE",
            "value":0.0
        }
    ],
    "availableTags":[
        
    ]
}



相关的参考文档
https://www.iocoder.cn/Spring-Boot/Actuator/?self



