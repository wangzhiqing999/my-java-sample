# test-service

基于 **Spring Boot 4.1.0 + MyBatis-Plus + PostgreSQL** 的后端服务示例项目，集成了数据库存储过程调用、ECC 加解密、多客户 Profile 实现、定时任务等常用能力，可作为新项目搭建的参考脚手架。

---

## 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [环境要求](#环境要求)
- [安装步骤](#安装步骤)
- [数据库初始化](#数据库初始化)
- [配置说明](#配置说明)
- [使用示例](#使用示例)
- [Profile 多客户实现](#profile-多客户实现)
- [定时任务](#定时任务)
- [ECC 加解密](#ecc-加解密)
- [Docker 部署](#docker-部署)
- [项目结构](#项目结构)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

---

## 项目简介

本项目是一个 Spring Boot 后端服务的测试/示例工程，演示了以下核心场景：

- 通过 MyBatis 调用 PostgreSQL 存储过程/函数（含无参/有参、无返回/有返回、数组参数、JSON 参数与返回等）
- 基于 BouncyCastle 的 ECC（ECDH + AES/GCM）加解密
- 使用 Spring `@Profile` 实现一套接口、多套客户实现的灵活切换
- 基于 `@Scheduled` 的定时任务
- 基于 `@ConfigurationProperties` 的配置管理及默认值机制
- 通过 Docker 进行容器化部署

---

## 功能特性

| 模块 | 说明 |
|------|------|
| 存储过程调用 | 演示无参无返回、有参无返回、有参有返回、数组参数、JSON 参数/返回等多种 PG 函数调用方式 |
| ECC 加解密 | 使用 ECDH 密钥协商 + AES/GCM 加密，公钥加密、私钥解密 |
| 多客户 Profile | 一套接口多个实现，通过配置文件切换不同客户逻辑 |
| 定时任务 | 基于 `@Scheduled` + cron 表达式，支持每日定时执行 |
| 配置管理 | `@ConfigurationProperties` 绑定配置，支持默认值与配置覆盖（`/test/config` 接口可验证） |
| 健康检查 | `/test/health` 接口，通过数据库连通性检测服务状态 |
| 版本管理 | `/test/version` 接口，读取 MANIFEST.MF 中的版本信息 |
| 接口文档 | springdoc-openapi 自动生成：Swagger UI（`/swagger-ui.html`）+ OpenAPI 契约（`/v3/api-docs`） |
| 异步支持 | `@EnableAsync` 开启异步方法调用 |

---

## 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 4.1.0 | 主框架 |
| MyBatis-Plus | 3.5.16 | ORM（`mybatis-plus-spring-boot4-starter`） |
| PostgreSQL Driver | 随 Spring Boot 管理 | 数据库驱动 |
| BouncyCastle | 1.78.1 | ECC 加密提供者（`bcprov-jdk18on` / `bcpkix-jdk18on`） |
| java-jwt | 4.4.0 | JWT 工具 |
| springdoc-openapi | 3.0.3 | Swagger/OpenAPI 自动生成接口文档（Spring Boot 4 兼容线） |
| Jackson | 3.2.0 | JSON 序列化 |
| Lombok | 随 Spring Boot 管理 | 简化 Java POJO |
| JDK | 21 | 运行环境 |

---

## 环境要求

在开始之前，请确保本地已安装以下环境：

- **JDK 21**（Spring Boot 4.x 最低要求 JDK 17，本项目使用 JDK 21）
- **Maven 3.8+**
- **PostgreSQL 12+**（需提前创建数据库）
- **Docker**（可选，用于容器化部署）

---

## 安装步骤

### 1. 克隆代码

```bash
git clone <仓库地址>
cd my-work-test
```

### 2. 配置数据库连接

编辑 `src/main/resources/application.yml`，修改数据源信息：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://<数据库地址>:5432/<数据库名>
    username: <用户名>
    password: <密码>
    driver-class-name: org.postgresql.Driver
```

### 3. 初始化数据库

执行 [`sql/init.sql`](sql/init.sql) 完成建表和函数创建（幂等，可重复执行）：

```bash
psql -h <数据库地址> -p 5432 -U <用户名> -d <数据库名> -f sql/init.sql
```

> 数据库对象清单及维护规范见下方 [数据库初始化](#数据库初始化) 章节。

### 4. 编译打包

```bash
mvn clean package -DskipTests
```

打包完成后，在 `target/` 目录下生成 `test-service-1.1-SNAPSHOT.jar`。

### 5. 本地运行

```bash
java -jar target/test-service-1.1-SNAPSHOT.jar
```

或在开发阶段直接使用 Maven 运行：

```bash
mvn spring-boot:run
```

启动成功后，服务默认监听 **8080** 端口，访问 `http://localhost:8080/test/health` 验证。

---

## 数据库初始化

项目依赖的全部 PostgreSQL 对象（2 张表 + 8 个函数）已收敛为单一脚本 [`sql/init.sql`](sql/init.sql)，作为数据库结构的**单一事实来源**（DOC-P2-2）。脚本具有幂等性，可重复执行。

### 执行方式

```bash
psql -h <host> -p 5432 -U <user> -d <database> -f sql/init.sql
```

### 数据库对象清单

| 对象 | 类型 | 说明 | Mapper 调用点 |
|------|------|------|---------------|
| `test_log` | 表 | 测试日志表（自增主键 `log_id`、文本 `log_text`、时间 `created_at`） | — |
| `common_config` | 表 | 通用配置表（主键 `config_code`、JSON 值 `config_value`、`ctime`/`utime`） | — |
| `test_nop_nor()` | 函数 | 无参数无返回值，插入一条日志 | `TestMapper.callTestNopNor()` |
| `test_havep_nor(varchar)` | 函数 | 有参数无返回值，拼接后插入日志 | `TestMapper.callTestHavepNor()` |
| `test_havep_haver(varchar)` | 函数 | 有参数有返回值（bigint），返回新记录 ID | `TestMapper.testHavepHaver()` |
| `test_havep_haverj(varchar)` | 函数 | 有参数，返回 JSON `{code,msg,id}` | `TestMapper.testHavepHaverj()` |
| `test_havepj_haverj(json)` | 函数 | JSON 入参（含 `log_text`），返回 JSON `{code,msg,id}` | `TestMapper.testHavepjHaverj()` |
| `test_sum_array(int[])` | 函数 | 整型数组求和 | `TestMapper.testSumArray()` |
| `fn_save_config(varchar, json)` | 函数 | 配置 UPSERT（存在则更新值） | `TestMapper.fn_save_config()` |
| `fn_get_config(varchar)` | 函数 | 按编码查询配置值（无记录返回 NULL） | `TestMapper.fn_get_config()` |

### 变更规范

涉及数据库变更（新增/修改表、函数、索引等）时，**必须**同步完成以下三步，禁止只改其一：

1. 修改 `src/main/java/com/my/work/mapper/TestMapper.java` 中的 SQL 调用
2. 更新 [`sql/init.sql`](sql/init.sql)（在函数头部注释中补充 Mapper 调用点）
3. 同步更新上表「数据库对象清单」与 [安装步骤](#安装步骤) 中的执行说明

> 详细维护流程与命名约定见 [`sql/README.md`](sql/README.md)。

---

## 配置说明

核心配置文件为 `src/main/resources/application.yml`，主要配置项如下：

```yaml
server:
  port: 8080                    # 服务端口

spring:
  profiles:
    active: clientA,otherClientC # 多客户实现切换（详见 Profile 章节）
  application:
    name: test-service
  datasource:
    url: jdbc:postgresql://pve003:5432/postgres
    username: postgres
    password: 123456
    driver-class-name: org.postgresql.Driver

mybatis:
  mapper-locations: classpath*:db/*.xml

logging:
  file:
    path: ./logs
  level:
    root: info
    '[com.my]': debug

my:
  work:
    config:
      # ECC 私钥与公钥（PEM 格式）
      # ⚠️ 实际使用时请使用 openssl 自行生成密钥对，切勿直接复制示例密钥
      privateKeyPem: |
        -----BEGIN EC PRIVATE KEY-----
        ...
        -----END EC PRIVATE KEY-----
      publicKeyPem: |
        -----BEGIN PUBLIC KEY-----
        ...
        -----END PUBLIC KEY-----
      # 以下为配置默认值与配置文件覆盖的演示
      testBooleanDefaultValue2: false
      testIntDefaultValue2: 2048
      testStringDefaultValue2: new config value
```

### 配置默认值机制

`ConfigData` 类中定义了属性的默认值。当 `application.yml` 中未配置某属性时，使用类中的默认值；当配置文件中设置了值，则覆盖默认值。可通过 `/test/config` 接口验证此行为。

### 生成 ECC 密钥对

```bash
# 生成 ECC 私钥
openssl ecparam -name secp256k1 -genkey -noout -out priv_key_s.pem

# 导出 ECC 公钥
openssl ec -in priv_key_s.pem -pubout -out pub_key_s.pem
```

将生成的密钥内容填入 `application.yml` 的 `my.work.config.privateKeyPem` 和 `publicKeyPem`。

---

## 使用示例

启动服务后，可通过以下接口进行测试。

> **接口文档（DOC-P2-3）**：项目集成 springdoc-openapi 3.0.3（Spring Boot 4 兼容线），启动后自动生成接口文档：
> - **Swagger UI**（交互式文档，支持在线调试）：<http://localhost:8080/swagger-ui.html>
> - **OpenAPI JSON** 契约：<http://localhost:8080/v3/api-docs>
> - **OpenAPI YAML** 契约：<http://localhost:8080/v3/api-docs.yaml>

> **响应格式**：自 M-P1-2 修复起，所有接口统一返回 `CommonResult`（`{code, msg, data}`）。成功响应 `code=200`、`msg="success"`，业务数据放入 `data` 字段（对象/Map 序列化为 JSON 字符串，字符串直接放入）；异常由 `GlobalExceptionHandler` 兜底，返回 `code=400`（参数错误）或 `500`（服务器错误），`msg` 为通用提示。

### 基础测试

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/test/get` | 基础测试，调用存储过程，data=null |
| GET | `/test/health` | 健康检查，data 为 `{status, message}` 的 JSON 字符串 |
| GET | `/test/version` | 获取项目版本信息，data 为 `{version, projectName}` 的 JSON 字符串 |

```bash
# 基础测试
curl http://localhost:8080/test/get

# 健康检查
curl http://localhost:8080/test/health

# 版本信息
curl http://localhost:8080/test/version
```

### 配置存取

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/test/save-config?code=1&msg=test_message` | 保存配置到数据库（POST，参数经 query string 传入；`code` 必填且≥0、`msg` 非空白，Bean Validation 校验失败返回 400） |
| GET | `/test/read-config` | 从数据库读取配置（data 为配置信息 JSON 序列化） |
| GET | `/test/config` | 测试默认配置（data 为 `ConfigData` 各属性当前值的拼接字符串，用于验证配置文件覆盖默认值机制） |

```bash
# 保存配置（POST，query 参数）
curl -X POST "http://localhost:8080/test/save-config?code=1&msg=hello"

# 读取配置
curl http://localhost:8080/test/read-config

# 查看默认配置与配置覆盖结果
curl http://localhost:8080/test/config
```

### ECC 加解密

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/test/encrypt` | 使用公钥加密数据 |
| POST | `/test/decrypt` | 使用私钥解密数据 |
| POST | `/test/save-log` | 解密数据后调用存储过程保存日志 |

```bash
# 加密
curl -X POST http://localhost:8080/test/encrypt \
  -H "Content-Type: application/json" \
  -d '{"log_text":"这是使用公钥加密的数据"}'

# 返回 Base64 编码的加密结果，例如：
# AFgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAAT6sembKQ/RLhvi4xZX4m5g0U+f7EGQuh5g...

# 解密（将上一步返回的密文作为 Body）
curl -X POST http://localhost:8080/test/decrypt \
  -H "Content-Type: text/plain" \
  -d 'AFgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAAT6sembKQ/RLhvi4xZX4m5g...'

# 返回原始明文：{"log_text":"这是使用公钥加密的数据"}

# 解密并保存日志（先解密，再将明文 JSON 传给存储过程）
curl -X POST http://localhost:8080/test/save-log \
  -H "Content-Type: text/plain" \
  -d 'AFgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAAT6sembKQ/RLhvi4xZX4m5g...'

# 返回 success，可在数据库 test_log 表中查看保存的记录
```

### Profile 多客户测试

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/test/info` | 获取 ClientService 当前激活实现的客户信息 |
| GET | `/test/other-info` | 获取 OtherClientService 当前激活实现的客户信息 |
| GET | `/test/both` | 获取两个接口当前激活实现合并后的待办列表 |

### 定时任务测试

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/test/daily-task` | 手动触发每日任务逻辑 |

```bash
curl http://localhost:8080/test/daily-task
# 返回：任务执行成功
```

---

## Profile 多客户实现

本项目演示了「一套接口、多套实现」的场景：同一套代码发布给不同客户，各客户有各自的业务逻辑实现，通过 `application.yml` 中的 `spring.profiles.active` 切换。

### 接口与实现

| 接口 | 实现类 | Profile 值 |
|------|--------|-----------|
| `ClientService` | `ClientAServiceImpl` | `clientA` |
| `ClientService` | `ClientBServiceImpl` | `clientB` |
| `ClientService` | `ClientEmptyServiceImpl` | （空实现/默认） |
| `OtherClientService` | `OtherClientCServiceImpl` | `otherClientC` |
| `OtherClientService` | `OtherClientDServiceImpl` | `otherClientD` |
| `OtherClientService` | `OtherClientEmptyServiceImpl` | （空实现/默认） |

### 切换方式

修改 `application.yml`：

```yaml
spring:
  profiles:
    # 启用客户 A + 客户 C 的实现
    active: clientA,otherClientC

    # 或：启用客户 B + 乙行业空实现
    # active: clientB,otherClientEmpty
```

修改后重启项目，访问 `/test/info` 或 `/test/both` 即可看到不同实现的返回结果。

---

## 定时任务

定时任务基于 Spring `@Scheduled` + cron 表达式实现。

### 启用方式

在启动类上添加 `@EnableScheduling` 注解（本项目已在 `Application.java` 中启用）。

### 配置示例

```java
@Scheduled(cron = "0 * * * * ?")  // 每分钟执行一次（测试用）
public void callDailyTask() {
    String result = testService.dailyTask();
    log.info("定时任务调用结果：{}", result);  // 使用占位符，避免字符串拼接
}
```

> **注意**：`0 * * * * ?` 为每分钟执行一次，仅用于测试观察。生产环境请根据实际需求修改 cron 表达式，例如 `0 0 8 * * ?` 表示每天 8 点执行。

---

## ECC 加解密

### 原理

本项目使用 **ECDH 密钥协商 + AES/GCM 对称加密** 的组合方案：

1. **加密**（公钥方）：生成临时 ECC 密钥对 → 用临时私钥与接收方公钥做 ECDH 协商得到共享密钥 → 用共享密钥派生 AES 密钥 → 使用 AES/GCM 加密明文 → 返回（临时公钥 + IV + 密文）的 Base64 编码
2. **解密**（私钥方）：解析出临时公钥 → 用自己的私钥与临时公钥做 ECDH 协商得到相同的共享密钥 → 用 AES/GCM 解密

### 密钥生成

```bash
openssl ecparam -name secp256k1 -genkey -noout -out priv_key_s.pem
openssl ec -in priv_key_s.pem -pubout -out pub_key_s.pem
```

### 业务流程

```
客户端                              服务端
  │                                   │
  │── 1. 使用公钥加密数据 ──────────> │  POST /test/encrypt（测试用）
  │<── 返回 Base64 密文 ──────────── │
  │                                   │
  │── 2. 提交密文 ──────────────────> │  POST /test/save-log
  │   （服务端用私钥解密后存库）       │
  │<── 返回 success ───────────────── │
```

---

## Docker 部署

### 1. 本地打包

```bash
mvn clean package -DskipTests
```

### 2. 准备部署文件

将以下文件复制到服务器同一目录：

```
application.yml
Dockerfile
test-service-1.1-SNAPSHOT.jar
```

> 说明：在本地完成编译打包，Docker 构建时不再执行编译，加快镜像构建速度。

### 3. 构建镜像

```bash
docker build -t test-service:1.1 .
```

### 4. 运行容器

```bash
docker run -d \
  --name test-service \
  -p 8081:8080 \
  --add-host=pve003:192.168.1.103 \
  -v ${PWD}/application.yml:/app/config/application.yml \
  --restart=always \
  test-service:1.1
```

| 参数 | 说明 |
|------|------|
| `-p 8081:8080` | 端口映射，宿主机 8081 → 容器 8080 |
| `--add-host=pve003:192.168.1.103` | 当配置文件中数据库地址使用机器名时，需通过此参数指定 IP 映射 |
| `-v ${PWD}/application.yml:/app/config/application.yml` | 挂载外部配置文件，方便修改无需重新构建镜像 |
| `--restart=always` | 容器异常时自动重启 |

### 5. 验证

```bash
curl http://localhost:8081/test/health
```

---

## 项目结构

```
src/main/java/com/my/work/
├── Application.java                 # 启动类（@EnableAsync, @EnableScheduling）
├── controller/
│   └── TestController.java          # REST 控制器（/test/*）
├── config/
│   ├── ConfigData.java             # 配置属性绑定（@ConfigurationProperties）
│   └── SecurityConfig.java         # 安全配置（@Bean 注册 BouncyCastle Provider，PERF-P2-1）
├── exception/
│   └── GlobalExceptionHandler.java # 全局异常处理（@RestControllerAdvice 统一兜底）
├── mapper/
│   └── TestMapper.java             # MyBatis Mapper（数据库操作）
├── model/
│   ├── CommonResult.java           # 通用返回结果（code/msg/data）
│   ├── SaveConfigRequest.java      # 保存配置请求参数 DTO（Bean Validation 校验）
│   └── VersionResponse.java        # 版本信息响应体（version/projectName）
├── sec/
│   ├── ECCCrypto.java              # ECC 加解密（ECDH + AES/GCM）
│   └── ECCKeyReader.java           # PEM 密钥读取
├── service/
│   ├── TestService.java            # 测试服务接口
│   ├── ClientService.java          # 客户服务接口（甲行业）
│   ├── OtherClientService.java     # 客户服务接口（乙行业）
│   └── impl/                       # 各接口实现类（@Profile 区分）
├── task/
│   └── TestScheduledTask.java      # 定时任务（@Scheduled）
└── util/
    ├── JsonUtil.java                # JSON 工具（Jackson 3.x）
    ├── AesGcmUtils.java             # AES-GCM 加解密（认证加密，替代不安全的 ECB）
    ├── AesUtil.java                 # AES-CBC 工具（随机 IV，Base64(IV+密文)）
    ├── RsaUtil.java                 # RSA 加解密
    ├── Md5Util.java                 # MD5 工具
    ├── Sha256Util.java              # SHA-256 工具
    ├── HmacSha256Util.java          # HMAC-SHA256 工具
    └── PemUtils.java                # PEM 文件工具
src/main/resources/
├── application.yml                 # 主配置文件
└── logback-spring.xml              # 日志配置
sql/
├── init.sql                        # 数据库初始化脚本（表 + 函数，单一事实来源）
└── README.md                       # SQL 脚本维护规范（变更流程/命名约定）
src/test/java/com/my/work/
├── util/           # 工具类单元测试（JUnit 5，7 个测试类：AesGcmUtils/AesUtil/RsaUtil/HmacSha256Util/Md5Util/PemUtils/Sha256Util）
├── service/        # Service 测试（TestServiceImplTest 加解密 roundtrip + TestServiceImplCoreTest 核心方法，12 用例）
├── controller/     # Controller 集成测试（TestControllerTest，14 用例，JDK Proxy 桩）
└── model/          # 参数校验测试（SaveConfigRequestTest，4 用例）
```

---

## 贡献指南

欢迎为本项目贡献代码！请遵循以下流程：

### 1. Fork 仓库

将项目 Fork 到你自己的 GitHub 账号下，然后 Clone 到本地：

```bash
git clone https://github.com/<你的用户名>/my-work-test.git
cd my-work-test
```

### 2. 创建分支

为你的修改创建一个语义清晰的分支：

```bash
git checkout -b feature/your-feature-name
# 或
git checkout -b fix/your-bug-fix
```

### 3. 编码规范

- 遵循项目现有的代码风格和命名约定
- 包名统一使用 `com.my.work.*` 下的子包
- Service 接口放在 `service/` 目录，实现类放在 `service/impl/` 目录
- 使用 `@Profile` 区分不同客户实现时，Profile 命名需语义清晰
- 新增 Mapper 方法需添加 Javadoc 注释，说明参数和返回值
- 配置项统一放在 `ConfigData` 类中管理，并添加默认值和注释
- 善用 Lombok（`@Data`、`@Slf4j` 等）减少样板代码

### 4. 提交代码

提交信息请遵循以下格式：

```
<type>: <简要描述>

<可选的详细说明>
```

常用 type：`feat`（新功能）、`fix`（修复）、`docs`（文档）、`refactor`（重构）、`chore`（杂项）

```bash
git add .
git commit -m "feat: 添加新的存储过程调用示例"
```

### 5. 推送并提交 Pull Request

```bash
git push origin feature/your-feature-name
```

然后在 GitHub 上发起 Pull Request，描述你的修改内容和目的。

### 6. 代码审查

提交 PR 后，请耐心等待维护者审查。审查通过后会合并到主分支。如有修改建议，请及时更新代码。

### 贡献注意事项

- 提交前确保代码能正常编译：`mvn clean compile`
- 不要提交与功能无关的格式化改动
- 涉及数据库变更时，需同步更新 `sql/init.sql` 及 README 中的数据库对象清单（详见 [数据库初始化](#数据库初始化) 变更规范）
- 涉及新增接口时，需更新本 README 中的接口文档表格
- **安全提醒**：切勿在代码或配置中提交真实的密钥、密码等敏感信息，示例中的密钥仅供演示

---

## 许可证

本项目仅供学习与参考使用。
