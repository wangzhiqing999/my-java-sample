# 项目长期备忘

## 项目概况
- 路径：`D:\My-Github\my-java-sample\my-work-test`
- Spring Boot 4.1.0（parent 管理版本）+ JDK 21 目标 + MyBatis-Plus + PostgreSQL + Lombok + Jackson 3.2.0 + BouncyCastle + java-jwt + springdoc-openapi 3.0.3
- 源文件 31 个（src/main）+ 测试 11 个（src/test，JUnit 5，**51 用例**）
- 代码审查基线（CODE_STANDARDS_AND_REVIEW_CHECKLIST.md）：P0 仅剩 **FN-P0-2/3 未评估**（SEC-P0 全清零、EXC-P0 全清零、**FN-P0-1 已完成**——核心路径异常处理审查 + testSaveConfig 吞异常修复）、P1 已修复（构造器注入/日志占位符/@Override/方法命名/HTTP 方法注解/println 清除/DES 删除/MD5 评估保留/参数校验 DTO/业务下沉 Service/**public 方法 Javadoc 完整**/**Service+Controller 测试**）、**P2 已全部清零**（main 移除 + JUnit 迁移 + JsonUtil import + VersionResponse 提取 + kebab-case + Record 简化 + Provider @Bean 注册 + JSON 解析方法提取 + README 接口文档同步 + SQL 脚本单一来源化 + **Swagger/OpenAPI 自动文档**）

## 环境约束与本地验证方式（重要）
- 本机**无 mvn / 无 JDK21**，仅 JDK17：`C:/Program Files/Microsoft/jdk-17.0.19+10/bin/javac.exe`
- m2 仓库缺依赖，每次需下载到 `target/deps/`（Windows 路径，不能用 /tmp）：
  bcpkix-jdk18on-1.78.1、bcprov-jdk18on-1.78.1（org/bouncycastle）、
  jackson-databind-3.2.0、jackson-core-3.2.0（tools/jackson/core）、
  jackson-annotations-**2.22**（**必须 2.22**：databind 3.2.0 运行时反射加载 `JsonApplyView`，2.21 及更早版本缺失会 ClassNotFoundException；2.22 同时含 JsonSerializeAs/JsonDeserializeAs；m2 里的 jackson-annotations-2.19.1 必须从 classpath 排除，否则版本混淆）、
  java-jwt-4.4.0（com/auth0）、
  **springdoc/swagger 6 个**（DOC-P2-3）：springdoc-openapi-starter-common/webmvc-api/webmvc-ui **3.0.3** + swagger-annotations/models/core-jakarta **2.2.47**（io/swagger/core/v3；版本来自 springdoc 父 POM 属性 `swagger-api.version`；3.x 无 springdoc-openapi-core 模块，核心类在 starter-common）
- javac 必须 `-encoding UTF-8`（否则 GBK 乱码）；classpath 用 `D:/...` Windows 路径（POSIX /d/ 不识别）
- **JUnit 本地运行**：下载 `junit-platform-console-standalone-1.11.4.jar` 到 target/deps →
  编译 main 到 `target/javac-check`、test 到 `target/test-check` →
  `java -jar target/deps/junit-platform-console-standalone-1.11.4.jar execute --class-path "target/javac-check;target/test-check;<全部依赖jar>" --scan-class-path "target/javac-check;target/test-check"`
- **坑**：`--scan-class-path` 无参数会扫描整个 classpath（m2 几百个 jar）产生"幽灵失败"（jdt ClassNotFound 副作用），**必须显式指定扫描目录**；运行时 classpath 需带全依赖 jar（否则 PemUtils 等报 NoClassDefFoundError）；**cp 生成时 m2 用 `! -path "*jackson-annotations*"` 排除**（m2 只有 2.19.1，缺 JsonApplyView），让 target/deps 的 2.22 生效
- 分层测试模式（TEST-P1-2/3）：无 spring-test/MockMvc/mockito → Controller/Service 测试用 JDK 动态代理桩（`Proxy.newProxyInstance` + 方法名 switch 分派 + Object 方法放行），直接 `new TestController(stub, stub, stub)` 调端点方法验证响应结构与依赖编排

## 关键代码模式
- AES-GCM：`AesGcmUtils.encrypt(plain, key)` → `Base64(12字节随机IV + 密文)`，16/24/32 字节密钥，GCM 认证拒绝错误密钥/篡改
- AES-CBC：`AesUtil.doEncrypt/doDecrypt(plain, key)` → `Base64(16字节随机IV + 密文)`
- RSA：`RsaUtil` RSA/ECB/PKCS1Padding，公钥 X509 Base64、私钥 PKCS8 Base64
- 构造器注入：`@RequiredArgsConstructor` + `private final`（替代 @Autowired）
- 统一异常：Controller/Service 异常上抛，`GlobalExceptionHandler`（@RestControllerAdvice）统一转换，客户端只见通用消息；参数绑定/校验异常（MethodArgumentNotValid/Bind/ConstraintViolation/HttpMessageNotReadable/参数缺失与类型不匹配）统一 400
- **核心路径异常处理（FN-P0-1）**：数据库写操作**禁止吞异常**（原 `testSaveConfig` catch 后静默返回、Controller 误报 200 success）——统一模式为签名 `throws Exception` + 异常上抛由 `GlobalExceptionHandler` 兜底 500；纯数据库读链路（`test()` 8 个 Mapper 调用）异常为 MyBatis 运行时异常，Javadoc 声明 `@throws RuntimeException` 即可无需 checked 声明；健康检查（try-catch 返 DOWN）与 MANIFEST 读取（catch 返默认值）属"降级语义"场景允许 catch
- 参数校验：接口入参用 DTO + `@Valid`（SaveConfigRequest 模式），`@RequestBody String` 参数级 `@NotBlank`；`spring-boot-starter-validation` 已在 pom
- 分层职责：Controller 仅 HTTP 层（参数校验 + 响应包装），密钥读取/加解密/MANIFEST 解析等业务逻辑一律在 Service（M-P1-1）；Service 单测用 JDK `Proxy` 桩替代 mockito（m2 无 mockito/byte-buddy）
- Javadoc 规范（M-P1-5）：全部 public 方法/构造器须有完整 Javadoc（@param/@return/@throws 与签名一致）；静态校验脚本思路——提取参数名时先剔除方法参数中的注解再按逗号拆分（避免 `@NotBlank(message=...)` 括号截断误报）；**`public record` 声明行须排除**（组件参数非方法参数，Record 自动访问器不在源码检查范围）
- **模型层 Record 模式（Q-P2-2）**：`CommonResult`/`VersionResponse` 为 `record`（不可变、自动 equals/hashCode/toString）；`CommonResult` 保留双参便捷构造器 `(int code, String msg)` → `this(code, msg, null)`，**删除无参构造器**（可变场景直接改用带参构造）；访问器是 `code()/msg()/data()` 而非 `getCode()`；类级 Javadoc 用 `@param` 标注组件；**Jackson 3.x 原生支持 Record 序列化/反序列化**（组件名即 JSON 字段名，规范构造器用于反序列化），API 契约不变
- 工具类（util 包）均为 final/私有构造或静态工具；无生产调用点的弱算法类直接删除（AesEcbUtils、DesEncryptor）；MD5（Md5Util）仅作非安全通用摘要保留，Javadoc 已加禁止密码哈希/签名警示
- **BouncyCastle Provider 注册（PERF-P2-1）**：由 `config/SecurityConfig`（`@Configuration` + `@Bean`）统一注册，替代此前 `ECCCrypto`/`ECCKeyReader` 中的 `static` 块；两类各保留 `private static void ensureProvider()` 惰性兜底（非 Spring 环境——如直接 `new TestServiceImpl` 的单元测试——首次调用时 `Security.getProvider("BC")==null` 则注册，Spring 环境下为 no-op）
- **SQL 脚本单一来源（DOC-P2-2）**：项目数据库对象收敛于 `sql/init.sql`（`test_log`/`common_config` 表 + 8 个函数，幂等可重复执行，函数头注释标注 Mapper 调用点）；维护规范见 `sql/README.md`——**数据库变更三步**：改 `TestMapper` → 改 `sql/init.sql` → 更新 README「数据库对象清单」表；函数命名小写蛇形、参数 `p_` 前缀
- **Swagger/OpenAPI（DOC-P2-3）**：springdoc-openapi **3.x 才兼容 Spring Boot 4**（2.x 仅支持 Boot 3）；**Spring Boot 4 必须显式开启** `springdoc.api-docs.enabled=true` + `springdoc.swagger-ui.enabled=true` 且配 `path`；文档入口 `config/OpenApiConfig`（`@OpenAPIDefinition` + `GroupedOpenApi` 分组 `public-api` 匹配 `/test/**`）；Controller 用 `@Tag`/`@Operation(summary)`、模型用 `@Schema(description/example)` 增强中文文档；访问地址 `/swagger-ui.html`、`/v3/api-docs`、`/v3/api-docs.yaml`

## 业务约束
- `TestMapper.testSumArray` 的 `@Select("SELECT test_sum_array(#{datas})")` 中 `test_sum_array` 是 **PostgreSQL 函数名**，不得改名
- pgcrypto 兼容加密（如需）用 SQL 侧 `encrypt()/decrypt()` 实现，不依赖 Java 工具类
- 涨红跌绿（A 股约定）；货币默认 ¥
