在 Spring Boot 监控端点 Actuator - health 端点


创建 Spring Starter 项目
名称：studyspringactuator2



在选择包的时候。

选择 Web 下面的 Spring Web
选择 Ops 下面的 Spring Boot Actuator



将 src\main\resources 目录下的 application.properties 修改为 application.yaml
填写 Actuator 的配置


新增一个 DemoHealthIndicator， 用于模拟  自定义的  健康检查处理逻辑。



在 studyspringactuator2 目录下，运行
mvnw spring-boot:run


访问
http://localhost:8080/actuator/health


初始化的结果，是简单返回 {"status":"UP"}

本项目修改后的结果


DemoHealthIndicator 检查失败的情况:
{
    "status":"DOWN",
    "components":{
        "demo":{
            "status":"DOWN",
            "details":{
                "msg":"我就是做个示例而已"
            }
        },
        "diskSpace":{
            "status":"UP",
            "details":{
                "total":1024191361024,
                "free":911350235136,
                "threshold":10485760,
                "exists":true
            }
        },
        "ping":{
            "status":"UP"
        }
    }
}


DemoHealthIndicator 检查成功的情况:
{
    "status":"UP",
    "components":{
        "demo":{
            "status":"UP"
        },
        "diskSpace":{
            "status":"UP",
            "details":{
                "total":1024191361024,
                "free":911350226944,
                "threshold":10485760,
                "exists":true
            }
        },
        "ping":{
            "status":"UP"
        }
    }
}

