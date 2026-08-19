# 项目长期备忘

## 项目概况
- 路径：`D:\My-Github\my-java-sample\my-work-test`
- Spring Boot 4.1.0（parent 管理版本）+ JDK 21 目标 + MyBatis-Plus + PostgreSQL + Lombok + Jackson 3.2.0 + BouncyCastle + java-jwt
- 源文件 28 个（src/main）+ 测试 7 个（src/test，JUnit 5，19 用例）
- 代码审查基线（CODE_STANDARDS_AND_REVIEW_CHECKLIST.md）：P0 全部清零、P1 已修复（构造器注入/日志占位符/@Override/方法命名/HTTP 方法注解/println 清除/DES 删除）、P2 已修复（main 移除 + JUnit 迁移）

## 环境约束与本地验证方式（重要）
- 本机**无 mvn / 无 JDK21**，仅 JDK17：`C:/Program Files/Microsoft/jdk-17.0.19+10/bin/javac.exe`
- m2 仓库缺 6 个依赖，每次需下载到 `target/deps/`（Windows 路径，不能用 /tmp）：
  bcpkix-jdk18on-1.78.1、bcprov-jdk18on-1.78.1（org/bouncycastle）、
  jackson-databind-3.2.0、jackson-core-3.2.0（tools/jackson/core）、
  jackson-annotations-2.21（**必须 2.21 无 .0 后缀**，含 JsonSerializeAs）、
  java-jwt-4.4.0（com/auth0）
- javac 必须 `-encoding UTF-8`（否则 GBK 乱码）；classpath 用 `D:/...` Windows 路径（POSIX /d/ 不识别）
- **JUnit 本地运行**：下载 `junit-platform-console-standalone-1.11.4.jar` 到 target/deps →
  编译 main 到 `target/javac-check`、test 到 `target/test-check` →
  `java -jar target/deps/junit-platform-console-standalone-1.11.4.jar execute --class-path "target/javac-check;target/test-check;<全部依赖jar>" --scan-class-path "target/javac-check;target/test-check"`
- **坑**：`--scan-class-path` 无参数会扫描整个 classpath（m2 几百个 jar）产生"幽灵失败"（jdt ClassNotFound 副作用），**必须显式指定扫描目录**；运行时 classpath 需带全依赖 jar（否则 PemUtils 等报 NoClassDefFoundError）

## 关键代码模式
- AES-GCM：`AesGcmUtils.encrypt(plain, key)` → `Base64(12字节随机IV + 密文)`，16/24/32 字节密钥，GCM 认证拒绝错误密钥/篡改
- AES-CBC：`AesUtil.doEncrypt/doDecrypt(plain, key)` → `Base64(16字节随机IV + 密文)`
- RSA：`RsaUtil` RSA/ECB/PKCS1Padding，公钥 X509 Base64、私钥 PKCS8 Base64
- 构造器注入：`@RequiredArgsConstructor` + `private final`（替代 @Autowired）
- 统一异常：Controller/Service 异常上抛，`GlobalExceptionHandler`（@RestControllerAdvice）统一转换，客户端只见通用消息
- 工具类（util 包）均为 final/私有构造或静态工具；无生产调用点的弱算法类直接删除（AesEcbUtils、DesEncryptor）

## 业务约束
- `TestMapper.testSumArray` 的 `@Select("SELECT test_sum_array(#{datas})")` 中 `test_sum_array` 是 **PostgreSQL 函数名**，不得改名
- pgcrypto 兼容加密（如需）用 SQL 侧 `encrypt()/decrypt()` 实现，不依赖 Java 工具类
- 涨红跌绿（A 股约定）；货币默认 ¥
