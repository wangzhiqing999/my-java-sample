在 Spring Boot 监控端点 Actuator - metrics 端点


创建 Spring Starter 项目
名称：studyspringactuator4



在选择包的时候。

选择 Web 下面的 Spring Web
选择 Ops 下面的 Spring Boot Actuator



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Actuator 的配置


新增一个 DemoInfoContributor， 用于演示一个 自定义指标 信息 。




在 studyspringactuator4 目录下，运行
mvnw spring-boot:run


访问
http://127.0.0.1:8080/actuator/metrics
返回的名称列表中，能够看到 自定义的 "demo.visit.count"

访问
http://127.0.0.1:8080/actuator/metrics/demo.visit.count
返回
{
    "name":"demo.visit.count",
    "description":"demo 访问次数",
    "baseUnit":"次",
    "measurements":[
        {
            "statistic":"COUNT",
            "value":0.0
        }
    ],
    "availableTags":[
        {
            "tag":"test",
            "values":[
                "nicai"
            ]
        },
        {
            "tag":"application",
            "values":[
                "demo-application"
            ]
        }
    ]
}

返回 http://127.0.0.1:8080/demo/visit  后， 再次刷新 http://127.0.0.1:8080/actuator/metrics/demo.visit.count
观察返回数据发生变化。



