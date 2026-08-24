# test-service 代码规范与 Review 检查清单

> 适用项目：Spring Boot 4.1.0 + JDK 21 + MyBatis-Plus + PostgreSQL  
> 维护人：Code Review Expert  
> 最后更新：2026-08-19

---

## 目录

- [第一部分：代码规范](#第一部分代码规范)
  - [1. 项目结构与包约定](#1-项目结构与包约定)
  - [2. 命名规范](#2-命名规范)
  - [3. 注入方式](#3-注入方式)
  - [4. 异常处理](#4-异常处理)
  - [5. 日志规范](#5-日志规范)
  - [6. 安全规范](#6-安全规范)
  - [7. 数据库与 Mapper 规范](#7-数据库与-mapper-规范)
  - [8. API 接口规范](#8-api-接口规范)
  - [9. 配置管理规范](#9-配置管理规范)
  - [10. 注释与文档规范](#10-注释与文档规范)
  - [11. 测试规范](#11-测试规范)
  - [12. 提交规范](#12-提交规范)
- [第二部分：Code Review 检查清单](#第二部分code-review-检查清单)
  - [P0 - 阻断项（Must Fix）](#p0---阻断项must-fix)
  - [P1 - 建议项（Should Fix）](#p1---建议项should-fix)
  - [P2 - 精进项（Nice to Have）](#p2---精进项nice-to-have)

---

# 第一部分：代码规范

## 1. 项目结构与包约定

```
com.my.work/
├── Application.java          # 启动类，只负责启动
├── controller/               # REST 控制器，只做参数接收与响应返回
├── service/                  # 服务接口
│   └── impl/                  # 服务实现
├── mapper/                    # MyBatis Mapper 接口
├── model/                     # 数据模型（DTO、VO、Entity）
├── config/                    # 配置类（@ConfigurationProperties 等）
├── sec/                       # 安全加解密相关
├── task/                      # 定时任务
├── util/                      # 工具类
└── exception/                 # 自定义异常（建议新增）
```

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| S-01 | Controller 不包含业务逻辑 | 只做参数校验、调用 Service、组装响应 |
| S-02 | Service 接口与实现分离 | 接口放 `service/`，实现放 `service/impl/` |
| S-03 | 一个类一个职责 | 不要在 Controller 中嵌套定义 DTO/VO 内部类（已有 `VersionResponse` 例外） |
| S-04 | 工具类应为 `final` + 私有构造 | 防止实例化与继承 |
| S-05 | 启动类只负责启动 | 不放业务代码，不定义业务 Bean |

---

## 2. 命名规范

### 2.1 通用规则

| 规则编号 | 规则 | 正例 | 反例 |
|---------|------|------|------|
| N-01 | 类名使用 UpperCamelCase | `ClientAServiceImpl` | `clientAServiceImpl` |
| N-02 | 方法名、变量名使用 lowerCamelCase | `getTodoList()` | `getTodoList_Data()` |
| N-03 | 常量使用 UPPER_SNAKE_CASE | `DEFAULT_AES_KEY` | `defaultAesKey` |
| N-04 | 包名全小写、单数 | `com.my.work.controller` | `com.my.work.controllers` |
| N-05 | Boolean 字段避免否定命名 | `isActive` | `isNotDisabled` |

### 2.2 特定命名约定

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| N-06 | Mapper 方法名遵循 camelCase | `testSumArray()` | ~~`test_sum_array()`~~ |
| N-07 | Profile 命名使用小驼峰 | `clientA`, `otherClientC` | `Client_A`, `client-a` |
| N-08 | 接口实现类以 `Impl` 结尾 | `ClientAServiceImpl` | `ClientAService` |
| N-09 | DTO/VO 后缀语义化 | `UserSaveDTO`, `UserVO` | `UserData` |
| N-10 | REST 端点路径使用小写短横线 | `/test/save-config` | `/test/saveConfig` |

> **当前项目待修正项**：已修复（Q-P1-5 ✅，2026-08-19）—— `TestMapper.test_sum_array()` 已改为 `testSumArray()`，`@Select` 中 PostgreSQL 函数名 `test_sum_array` 保留不动。

---

## 3. 注入方式

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| I-01 | **优先使用构造器注入** | 通过 `@RequiredArgsConstructor`（Lombok）实现 |
| I-02 | 避免字段注入 `@Autowired` | 字段注入不利于测试、隐藏依赖关系 |
| I-03 | `final` 字段配合构造器注入 | 保证依赖不可变 |

### 示例

```java
// ✅ 推荐
@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;
    private final ConfigData configData;

    // ...
}

// ❌ 不推荐
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;  // 字段注入，不利于测试

    @Autowired
    private ConfigData configData;
}
```

---

## 4. 异常处理

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| E-01 | **禁止吞掉异常** | catch 块必须处理异常（日志 + 抛出或返回明确错误） |
| E-02 | **禁止返回 `null` 表示错误** | 使用空对象、Optional 或抛出自定义异常 |
| E-03 | **禁止将异常信息直接返回给客户端** | 存在信息泄露风险 |
| E-04 | **禁止暴露完整堆栈给客户端** | 健康检查等接口不应返回 `stackTrace` |
| E-05 | 使用 `@ControllerAdvice` 统一异常处理 | 而非每个方法各自 try-catch |
| E-06 | catch 块中不应吞掉异常后返回 `null` | 要么记录后重抛，要么返回空对象/默认值 |

### 示例

```java
// ❌ 反例 1：异常信息直接返回客户端
@PostMapping("/encrypt")
public String encrypt(@RequestBody String originalText) {
    try {
        // ...
        return encryptedText;
    } catch (Exception e) {
        log.error("encrypt error!", e);
        return e.getMessage();  // 可能泄露内部信息
    }
}

// ❌ 反例 2：吞掉异常返回 null
public CommonResult testLoadConfig(String code) {
    try {
        // ...
    } catch (Exception ex) {
        log.error("保存配置信息发生错误...", ex);
        return null;  // 调用方无法区分"无数据"和"出错"
    }
}

// ❌ 反例 3：暴露堆栈
result.put("stackTrace", sw.toString());  // 安全风险

// ✅ 正例：统一异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        log.error("系统异常", e);
        return new CommonResult(500, "系统内部错误", null);
    }
}
```

---

## 5. 日志规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| L-01 | **禁止使用 `System.out.println`** | 统一使用 SLF4J（`@Slf4j`） |
| L-02 | 使用占位符 `{}` 而非字符串拼接 | `log.info("result: {}", id)` 而非 `log.info("result: " + id)` |
| L-03 | 敏感信息禁止记录日志 | 密钥、密码、手机号等脱敏后记录 |
| L-04 | 异常日志包含完整上下文 | 记录入参、操作类型 |
| L-05 | 日志级别合理使用 | ERROR=系统异常, WARN=可恢复异常, INFO=关键流程, DEBUG=调试信息 |

### 当前项目待修正项

> **状态（2026-08-19）**：下方案例为**反例教学示例**（展示错误用法）。实际代码中 `TestController` 的 println 已随 P0-4 清除，`TestServiceImpl`/`TestScheduledTask` 的日志拼接已随 P1 修复（Q-P1-3 ✅）。

```java
// TestController.java - 反例：使用了 System.out.println
System.out.println("原始文本: " + originalText);    // 应改为 log.debug
System.out.println("加密后: " + encryptedText);      // 应改为 log.debug
System.out.println("解密后: " + decryptedText);      // 应改为 log.debug

// TestServiceImpl.java - 反例：使用了字符串拼接
log.debug("解密后: " + decryptedText);  // 应改为 log.debug("解密后: {}", decryptedText)
log.info("定时任务调用结果：" + result);  // 应改为 log.info("定时任务调用结果：{}", result)
```

---

## 6. 安全规范

### 6.1 加密算法安全

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| SEC-01 | **禁止使用 AES-ECB 模式** | ECB 不安全，相同明文产生相同密文。使用 CBC 或 GCM |
| SEC-02 | **禁止使用固定 IV** | CBC 模式下 IV 必须随机生成且每次不同 |
| SEC-03 | **禁止硬编码密钥** | 密钥应从配置或密钥管理服务获取 |
| SEC-04 | **禁止使用 DES** | DES 密钥长度仅 56 位，已不安全。使用 AES |
| SEC-05 | **禁止使用 MD5 做密码哈希** | 使用 BCrypt/Argon2/PBKDF2 |
| SEC-06 | 加密密钥长度不少于 128 位 | AES-128 为最低要求 |
| SEC-07 | 字符串编码统一使用 UTF-8 | `getBytes(StandardCharsets.UTF_8)` |

### 当前项目待修正项

| 文件 | 问题 | 严重程度 |
|------|------|---------|
| ~~`AesEcbUtils.java`~~ | 硬编码默认密钥 `"it_is_a_test_pwd"` | ~~P0~~ ✅ 已删除 |
| ~~`AesEcbUtils.java`~~ | 使用 AES-ECB 模式（不安全） | ~~P0~~ ✅ 已删除 |
| ~~`AesUtil.java`~~ | 硬编码静态 IV `byte[] IV = {1, 2, 3, ...}` | ~~P0~~ ✅ 已修复（随机 IV） |
| ~~`DesEncryptor.java`~~ | 使用 DES 算法（已不安全） | ~~P1~~ ✅ 已删除 |
| ~~`Md5Util.java`~~ | MD5 不应用于密码哈希 | ~~P1~~ ✅ 已评估（仅通用摘要，未用于密码/签名，已加安全警示 Javadoc） |

### 6.2 接口安全

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| SEC-08 | 所有外部输入必须校验 | 使用 `@Valid` + `@Validated` |
| SEC-09 | SQL 注入防护 | 使用 MyBatis 参数化查询 `#{}`，禁止 `${}` 拼接 |
| SEC-10 | 敏感配置不硬编码在代码中 | 数据库密码、API Key 等通过环境变量或配置中心注入 |
| SEC-11 | 错误响应不泄露内部信息 | 返回通用错误消息，详情记录在日志中 |
| SEC-12 | 生产环境关闭 actuator 敏感端点 | 或配置安全认证 |

---

## 7. 数据库与 Mapper 规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| DB-01 | Mapper 方法名遵循 camelCase | `testSumArray()` 而非 `test_sum_array()` |
| DB-02 | 无返回值的操作使用 `@Update` | 有返回值的使用 `@Select`（当前项目约定） |
| DB-03 | 参数必须使用 `@Param` 注解 | 当方法有多个参数时 |
| DB-04 | 涉及写操作的方法添加 `@Transactional` | 保证事务一致性 |
| DB-05 | Mapper 方法必须添加 Javadoc | 说明参数含义与返回值 |
| DB-06 | 禁止使用 `${}` 拼接 SQL | 防止 SQL 注入，统一使用 `#{}` |
| DB-07 | 连接池配置合理 | 设置最大连接数、超时时间等 |
| DB-08 | 大字段查询注意分页 | 避免 `SELECT *` 查出全表 |

### 示例

```java
// ✅ 正例
/**
 * 调用 test_sum_array 函数，对数组元素求和.
 * @param datas 整型数组
 * @return 数组元素之和
 */
@Select("SELECT test_sum_array(#{datas})")
int testSumArray(@Param("datas") int[] datas);

// ❌ 反例
@Select("SELECT test_sum_array(#{datas})")
int test_sum_array(int[] datas);  // 命名不规范、缺少 @Param
```

---

## 8. API 接口规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| API-01 | 使用具体的 HTTP 方法 | `@GetMapping`, `@PostMapping` 而非通用的 `@RequestMapping` |
| API-02 | 请求参数使用对象接收 | 多个参数封装为 DTO，使用 `@Valid` 校验 |
| API-03 | 统一响应格式 | 使用 `CommonResult` 包装所有 API 响应 |
| API-04 | 接口路径使用 RESTful 风格 | `/test/logs/{id}` 而非 `/test/getLogById?id=xxx` |
| API-05 | 使用 `@RequestBody` 接收 JSON | POST/PUT 请求体使用对象接收 |
| API-06 | 参数校验使用 Bean Validation | `@NotBlank`, `@NotNull`, `@Size` 等 |
| API-07 | 接口需要添加 Javadoc | 说明用途、参数、返回值 |

### 当前项目待修正项

> **状态（2026-08-20）**：`@RequestMapping` 混用已随 Q-P1-6 修复（`/get`、`/readconfig` → `@GetMapping`，`/saveconfig` → `@PostMapping`）；下方示例中"多个松散参数"一项已随 SEC-P1-3 修复（`TestController.saveConfig(int code, String msg)` 已封装为 `SaveConfigRequest` DTO + `@Valid`），其余为教学示例。

```java
// ❌ 使用了通用的 @RequestMapping
@RequestMapping("/get")
@RequestMapping("/saveconfig")
// ✅ 应明确 HTTP 方法
@GetMapping("/get")
@PostMapping("/saveconfig")

// ❌ 多个松散参数
public String saveConfig(int code, String msg) { ... }
// ✅ 封装为 DTO
public String saveConfig(@Valid @RequestBody SaveConfigDTO dto) { ... }
```

---

## 9. 配置管理规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| CFG-01 | 配置项统一管理 | 使用 `@ConfigurationProperties` 绑定，集中在 `ConfigData` 类 |
| CFG-02 | 每个配置项添加 Javadoc | 说明含义、默认值、取值范围 |
| CFG-03 | 敏感配置使用环境变量注入 | `${DB_PASSWORD:default}` |
| CFG-04 | 不同环境使用不同 Profile | `application-dev.yml`, `application-prod.yml` |
| CFG-05 | 配置项命名使用小写短横线 | `my.work.config.test-default-value` |

### 当前项目待修正项

> **状态（2026-08-19）**：已随 M-P1-4 修复 —— 下述 3 个字段均已补齐 Javadoc，示例保留作教学参考。

---

## 10. 注释与文档规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| DOC-01 | 所有 public 方法必须有 Javadoc | 包含 `@param`, `@return`, `@throws` |
| DOC-02 | 实现类方法必须添加 `@Override` | 明确标识覆盖父类/接口方法 |
| DOC-03 | 类注释说明职责 | 一句话说清这个类做什么 |
| DOC-04 | 复杂逻辑添加行内注释 | 解释"为什么"而非"做什么" |
| DOC-05 | 避免无意义注释 | 不要写 `// 获取名称` 在 `getName()` 上面 |

### 当前项目待修正项

> **状态（2026-08-20）**：已随 M-P1-5 修复 —— 全项目 65 个 public 方法/构造器均已具备完整 Javadoc（含 `@param`/`@return`/`@throws`），脚本静态校验通过。`@Override` 标注已随 Q-P1-4 完成，示例保留作教学参考。

---

## 11. 测试规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| T-01 | 核心业务逻辑必须有单元测试 | Service 层至少覆盖主要分支 |
| T-02 | 测试类与被测类同包 | `src/test/java/` 下包结构一致 |
| T-03 | 测试方法命名语义化 | `shouldReturnSuccessWhenValidInput()` |
| T-04 | 每个测试方法只验证一个行为 | 一个断言一个焦点 |
| T-05 | 使用 `@BeforeEach` 初始化测试数据 | 保持测试方法简洁 |
| T-06 | 加解密工具类必须有正向+逆向测试 | 加密后解密应得到原文 |
| T-07 | Mock 外部依赖 | Mapper、网络调用等使用 Mockito Mock |

> **状态（2026-08-20）**：工具类测试已随 Q-P2-1 补齐；Service 核心方法测试（TEST-P1-2，`TestServiceImplCoreTest` 8 用例）与 Controller 集成测试（TEST-P1-3，`TestControllerTest` 14 用例）已随本轮补齐，全量 49/49 通过。`ECCCrypto` 加解密对称性已由 `TestServiceImplTest.encrypt_decrypt_往返还原原文` 间接覆盖（真实密钥 roundtrip），暂不单独立类。

---

## 12. 提交规范

### 规则

| 规则编号 | 规则 | 说明 |
|---------|------|------|
| GIT-01 | 提交信息遵循约定格式 | `<type>: <描述>` |
| GIT-02 | 一次提交只做一件事 | 不要混合多个功能/修复 |
| GIT-03 | 提交前确保编译通过 | `mvn clean compile` |
| GIT-04 | 不提交敏感信息 | 密钥、密码、内部 IP 等 |
| GIT-05 | 分支命名语义化 | `feature/xxx`, `fix/xxx`, `refactor/xxx` |

### 提交类型

| 类型 | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | 修复缺陷 |
| `docs` | 文档变更 |
| `refactor` | 代码重构（不改变功能） |
| `test` | 测试相关 |
| `chore` | 构建、依赖、配置等杂项 |
| `security` | 安全修复 |

---

# 第二部分：Code Review 检查清单

> 使用说明：每次 PR 审查时逐项检查。P0 为阻断项，必须修复后才能合并；P1 为建议项，应尽量修复；P2 为精进项，可视情况处理。

## P0 - 阻断项（Must Fix）

> P0 项不修复则不允许合并。涉及安全漏洞、数据丢失、功能不可用等严重问题。

### 安全-P0

- [x] **SEC-P0-1**：代码中无硬编码的密钥、密码、Token
  - 检查范围：所有 `.java` 文件中的字符串常量（含工具类、配置类、JWT/签名类等）
  - 特别关注：~~`AesEcbUtils.DEFAULT_AES_KEY`~~、~~`AesUtil.IV`~~、~~`AppleClientSecret.PRIVATE_KEY_256`~~（均已修复）
  - 当前状态：**✅ 已修复（2026-08-19）** → 删除 `AesEcbUtils.java`（硬编码密钥 + ECB），新增 `AesGcmUtils.java`（密钥由调用方传入，不再提供默认密钥）；删除 `AppleClientSecret.java`（硬编码 EC P-256 私钥 + clientId/teamId/keyId 占位符 + 零调用点，与 `DesEncryptor` 同款处理，git 历史可查）

- [x] **SEC-P0-2**：不使用已知不安全的加密模式（AES-ECB）
  - ECB 模式相同明文产生相同密文，泄露数据模式
  - 当前状态：**✅ 已修复（2026-08-19）** → `AesGcmUtils.java` 使用 `AES/GCM/NoPadding` 认证加密模式

- [x] **SEC-P0-3**：对称加密的 IV 必须随机生成
  - 固定 IV 导致相同明文加密结果相同，丧失 CBC/GCM 的安全性
  - 当前状态：**✅ 已修复（2026-08-19）** → 重写 `AesUtil.java`：每次加密生成 16 字节随机 IV，返回 `Base64(IV + ciphertext)`，解密自动提取；密钥由调用方传入并校验 16/24/32 字节；移除 main 测试代码

- [x] **SEC-P0-4**：异常信息不直接返回给客户端
  - 可能泄露内部实现细节、堆栈信息、SQL 语句等
  - 当前状态：**✅ 已修复（2026-08-19）** → 新增 `exception/GlobalExceptionHandler.java`（`@RestControllerAdvice` 统一兜底：`IllegalArgumentException` → 400 + 通用参数错误提示；其余异常 → 500 + "服务器内部错误"，完整堆栈仅记录服务端日志）；`TestController.encrypt/decrypt/saveLogData` 移除 `return e.getMessage()`，异常上抛由全局处理器接管，并顺手将 `System.out.println` 改为 `log.debug`

- [ ] **SEC-P0-5**：SQL 查询不使用字符串拼接
  - 统一使用 `#{}` 参数化查询，禁止 `${}` 动态拼接
  - 当前状态：**通过** → 项目均使用 `#{}`

### 异常处理-P0

- [x] **EXC-P0-1**：禁止吞掉异常后返回 `null`
  - 调用方无法区分"正常无数据"和"异常"，可能导致 NPE
  - 当前状态：**✅ 已修复** → `TestServiceImpl.testLoadConfig()` 移除 catch，异常上抛（接口与 Controller 同步 `throws Exception`，由 `GlobalExceptionHandler` 统一处理）

- [x] **EXC-P0-2**：健康检查接口不暴露完整堆栈
  - `stackTrace` 字段可能泄露内部结构，被攻击者利用
  - 当前状态：**✅ 已修复** → `TestServiceImpl.health()` 移除 `stackTrace` 与 `e.getMessage()`，异常细节改 `log.error` 记录服务端日志，仅返回 `status=DOWN` + 通用提示

### 功能正确性-P0

- [x] **FN-P0-1**：核心业务路径有异常处理
  - 数据库操作、加解密、外部调用等关键路径不能缺少 try-catch 或 throws 声明
  - ✅ 已修复（2026-08-24）：逐项审查核心路径——① `TestServiceImpl.testSaveConfig()` **吞掉数据库写入异常**（catch 后仅记日志，Controller 仍返回 200 success，客户端误判保存成功）→ 移除 catch，异常上抛（接口/实现签名加 `throws Exception`，`TestController.saveConfig` 同步声明，三处 Javadoc 补 `@throws`，与 `testLoadConfig`/`saveLogData`/`encrypt`/`decrypt` 一致）；② `test()` 的 8 个数据库调用 Javadoc 明确"异常上抛由 `GlobalExceptionHandler` 兜底"（MyBatis 运行时异常无需声明，链路经 `@ExceptionHandler(Exception.class)` → 500 完整）；③ 复核确认其余核心路径已合规：加解密工具 `throws Exception`、健康检查 try-catch 返回 DOWN（不暴露异常）、MANIFEST 读取 catch 返回默认值、Client 实现为纯内存操作、定时任务无 IO 路径；新增回归用例 2 个（Service 层数据库写入异常上抛 + Controller 层异常穿透），JUnit 51/51 通过
- [ ] **FN-P0-2**：事务边界正确
  - 涉及多次写操作的方法必须添加 `@Transactional`
- [ ] **FN-P0-3**：资源正确关闭
  - 文件流、数据库连接等使用 try-with-resources

---

## P1 - 建议项（Should Fix）

> P1 项建议在当前 PR 或下一个 PR 中修复。涉及代码质量、可维护性、性能等。

### 代码质量-P1

- [x] **Q-P1-1**：使用构造器注入而非字段注入
  - 字段注入 `@Autowired` 不利于单元测试
  - 修正方式：使用 `@RequiredArgsConstructor` + `final` 字段
  - 当前状态：**✅ 已修复** → `TestController`(4 处), `TestServiceImpl`(2 处), `TestScheduledTask`(1 处) 全部改为 `@RequiredArgsConstructor` + `final` 字段，全项目无 `@Autowired` 残留

- [x] **Q-P1-2**：禁止使用 `System.out.println`
  - 统一使用 SLF4J 日志框架
  - ✅ 已修复（2026-08-19）：`Application.main()` → `log.info("start at {}")`；`PemUtils` 4 处 catch 块 println → 抛 `IllegalArgumentException`（顺带消除静默返回 null）；6 个工具类（`DesEncryptor`/`AppleClientSecret`/`Sha256Util`/`Md5Util`/`HmacSha256Util`/`RsaUtil`）main 自测代码整段删除（含 `AppleClientSecret` 的 `e.printStackTrace()`），全项目 0 残留。后续（2026-08-19）`AppleClientSecret` 整类删除（SEC-P0-1 漏检项，硬编码 EC 私钥），详见 P0 汇总表第 6 项

- [x] **Q-P1-3**：日志使用占位符 `{}`
  - 避免不必要的字符串拼接开销
  - ✅ 已修复（2026-08-19）：`TestServiceImpl.java:135`、`TestScheduledTask.java:33` 改为 `{}` 占位符

- [x] **Q-P1-4**：所有实现接口的方法标注 `@Override`
  - 编译器可帮助检查方法签名匹配
  - ✅ 已修复（2026-08-19）：`TestServiceImpl` 5 个方法（`saveLogData`/`dailyTask`/`testConfig`/`testSaveConfig`/`testLoadConfig`）+ `ClientBServiceImpl.getClientInfo()` 共 6 处补充 `@Override`，全项目 7 个实现类 19 个接口方法均已标注

- [x] **Q-P1-5**：方法命名遵循 Java 约定（camelCase）
  - ✅ 已修复（2026-08-19）：`TestMapper.test_sum_array()` → `testSumArray()`，调用点 `TestServiceImpl` 同步；`@Select` 中数据库函数名 `test_sum_array` 保留不动，不影响 PostgreSQL 存储过程调用

- [x] **Q-P1-6**：使用具体的 HTTP 方法注解
  - `@GetMapping` / `@PostMapping` 而非通用的 `@RequestMapping`
  - ✅ 已修复（2026-08-19）：`/get`→`@GetMapping`、`/readconfig`→`@GetMapping`、`/saveconfig`→`@PostMapping`（写操作语义）；类级 `@RequestMapping("/test")` 保留；readme 调用示例同步更新

### 安全-P1

- [x] **SEC-P1-1**：不使用 DES 算法
  - DES 已被破解，应使用 AES
  - ✅ 已修复（2026-08-19）：`DesEncryptor.java` 整类删除（无生产调用点；DES 弱算法 + 硬编码默认密钥 `it_is_a_test_password`；pgcrypto `des-cbc/pad:pkcs` 兼容加密改用 SQL 侧 `encrypt()/decrypt()` 实现）

- [x] **SEC-P1-2**：MD5 不用于密码哈希或签名
  - 存在碰撞风险，应使用 SHA-256 或 BCrypt
  - ✅ 已评估（2026-08-20）：`Md5Util` 生产代码零调用点，仅提供通用摘要 `calculateMD5`，未用于密码哈希或签名（无 HMAC-MD5/RSA-MD5 实现）；MD5 作为非安全场景摘要（数据指纹/旧系统兼容）予以保留，类与方法 Javadoc 已加安全警示（禁止密码哈希/签名/HMAC/证书指纹），防止未来误用

- [x] **SEC-P1-3**：接口输入参数校验
  - 使用 `@Valid` + Bean Validation 注解
  - ✅ 已修复（2026-08-20）：① 新建 `model/SaveConfigRequest`（`code` @NotNull+@Min(0)、`msg` @NotBlank），`saveConfig` 松散参数 `(int code, String msg)` 封装为 `@Valid @ModelAttribute SaveConfigRequest`；② Controller 类级 `@Validated`，`/encrypt` `/decrypt` `/save-log` 的 `@RequestBody String` 参数级 `@NotBlank`；③ `GlobalExceptionHandler` 新增参数绑定/校验异常统一 400 处理（`MethodArgumentNotValidException`/`BindException`/`ConstraintViolationException`/`HttpMessageNotReadableException`/`MissingServletRequestParameterException`/`MethodArgumentTypeMismatchException`）；④ 新增 `SaveConfigRequestTest` 4 用例（合法通过 + code 空/负 + msg 空白拒绝），JUnit 23/23 通过

- [x] **SEC-P1-4**：统一异常处理机制
  - 使用 `@RestControllerAdvice` + `@ExceptionHandler`
  - ✅ 已修复（2026-08-19）：新增 `exception/GlobalExceptionHandler.java`（`@RestControllerAdvice` 统一兜底：`IllegalArgumentException` → 400 + 通用参数错误提示，其余异常 → 500 + "服务器内部错误"，完整堆栈仅记录服务端日志）；`TestController` 各接口移除 `return e.getMessage()`，异常上抛由全局处理器接管

### 可维护性-P1

- [x] **M-P1-1**：Controller 不包含业务逻辑
  - 加解密逻辑应放在 Service 层
  - ✅ 已修复（2026-08-20）：`getVersion`（MANIFEST 解析）、`encrypt`（公钥读取 + ECC 加密）、`decrypt`（私钥读取 + ECC 解密）全部下沉至 `TestService`/`TestServiceImpl`；Controller 仅保留 HTTP 层调用，`configData` 依赖与相关 import 一并移除

- [x] **M-P1-2**：统一 API 响应格式
  - 所有接口返回 `CommonResult` 包装
  - ✅ 已修复（2026-08-19）：`TestController` 13 个接口全部返回 `CommonResult`（`get`/`getVersion`/`health`/`saveConfig`/`readConfig`/`encrypt`/`decrypt`/`saveLogData`/`dailyTask`/`testConfig`/`getClientInfo`/`getOtherClientInfo`/`getBoth`），Controller 层提供 3 个 `success(...)` 辅助方法构造成功响应，对象/Map 通过 `JsonUtil.toJson` 序列化后放入 `data` 字段；`GlobalExceptionHandler` 同步统一返回 `ResponseEntity<CommonResult>`（400/500），与正常响应格式一致；Service 层签名保持不变。grep 确认 13 个接口均为 `public CommonResult`。javac 编译验证 28 源文件全部通过（JDK 21 + Lombok 1.18.34）

- [x] **M-P1-3**：工具类声明为 `final` 并提供私有构造函数
  - ✅ 已修复（2026-08-19）：8 个工具类全部声明 `public final class` + 私有构造（含此前已修的 `AesGcmUtils`/`AesUtil`，本次补齐 `Md5Util`/`Sha256Util`/`HmacSha256Util`/`RsaUtil`/`PemUtils`/`JsonUtil` 6 个）；grep 确认 `com.my.work.util` 包下 8 个工具类全部 final + 私有构造

- [x] **M-P1-4**：配置项添加完整 Javadoc
  - ✅ 已修复（2026-08-19）：`ConfigData` 此前缺注释的 3 个字段（`testBooleanDefaultValue2`/`testIntDefaultValue2`/`testStringDefaultValue2`）均已补齐 Javadoc，全类字段注释完整

- [x] **M-P1-5**：public 方法添加 Javadoc
  - 包含 `@param`, `@return`, `@throws`
  - ✅ 已修复（2026-08-20）：全项目 65 个 public 方法/构造器全部具备 Javadoc，`@param`/`@return`/`@throws` 与签名一致（脚本校验通过）——补齐 13 处缺失（`TestService.test`/`ClientService.getTodoList`/`OtherClientService.getTodoList`/`TestController.getOtherClientInfo`/`TestMapper.selectTest`/`selectFunction`/`PemUtils` 3 方法/`ECCCrypto` 2 方法/`ECCKeyReader` 5 方法/`CommonResult` 3 构造器/`Application.main`/`TestServiceImpl.test`/`health`/`testSaveConfig`/6 个 Client impl 的 `getTodoList`），补全 15 处空 `@param`/`@return` 描述与 10 处缺失 `@throws`；顺手优化 `ECCCrypto`/`ECCKeyReader`/`PemUtils` 类级注释（去除"Deepseek 生成"字样）与 `JsonUtil`/`CommonResult`/`VersionResponse` 公共字段注释；javac 编译 29 源文件通过，JUnit 27/27 通过

### 测试-P1

- [x] **TEST-P1-1**：加解密工具有单元测试
  - ✅ 已修复（2026-08-19）：随 Q-P2-1 新增 7 个 JUnit 测试类，加解密工具覆盖 `AesGcmUtilsTest`（7 用例：16/24/32 密钥 roundtrip、随机 IV、错误密钥/篡改拒绝）、`AesUtilTest`（3 用例：roundtrip、错误密钥拒绝）、`RsaUtilTest`（2 用例：roundtrip、错误密钥拒绝）；本地 `junit-platform-console-standalone` 验证 19/19 通过
- [x] **TEST-P1-2**：Service 核心方法有单元测试
  - ✅ 已修复（2026-08-20）：新增 `TestServiceImplCoreTest` 8 用例——`test()` 全链路存储过程调用（8 个 Mapper 调用点断言）、`health()` UP/DOWN 双路径、`testConfig()` 默认配置拼接、`dailyTask()`、`saveLogData()` 解密后入库、`testSaveConfig()` 编码透传、`testLoadConfig()` JSON 反序列化；配合既有 `TestServiceImplTest` 4 用例（加解密 roundtrip）覆盖 Service 全部 9 个核心方法；Mapper 用 JDK 动态代理桩替代（无 mockito 环境）
- [x] **TEST-P1-3**：Controller 有集成测试
  - ✅ 已修复（2026-08-20）：新增 `TestControllerTest` 14 用例——13 个端点方法全覆盖（`get`/`getVersion`/`health`/`saveConfig`/`readConfig`/`encrypt`/`decrypt`/`saveLogData`/`dailyTask`/`config`/`info`/`otherInfo`/`both`）+ 异常穿透路径（Service 抛异常时 Controller 不吞异常）；验证统一响应 `code=200,msg=success`、data 包装（字符串直放/对象与 Map JSON 序列化）、依赖编排（桩调用计数）；三个 Service 依赖全部用 JDK 动态代理桩（本机无 spring-test/MockMvc，直接实例化 Controller 调用端点方法）

---

## P2 - 精进项（Nice to Have）

> P2 项是优化建议，不影响功能和安全，可在代码质量提升阶段处理。

### 代码质量-P2

- [x] **Q-P2-1**：移除 main 方法中的测试代码
  - 工具类中的 `main` 方法仅用于测试，应迁移到单元测试
  - ✅ 已修复（2026-08-19）：6 个工具类 main 随 Q-P1-2 删除；pom 新增 `spring-boot-starter-test`（test），新增 7 个 JUnit 测试类（`AesGcmUtilsTest`/`AesUtilTest`/`Sha256UtilTest`/`Md5UtilTest`/`HmacSha256UtilTest`/`RsaUtilTest`/`PemUtilsTest`，共 19 用例），本地 `junit-platform-console-standalone` 验证 19/19 通过

- [x] **Q-P2-2**：使用 Java 21 特性简化代码
  - Record 类替代简单 DTO（`CommonResult`, `VersionResponse`）
  - Pattern Matching、Switch Expressions
  - Text Block 处理多行字符串
  - ✅ 已修复（2026-08-24）：`CommonResult`/`VersionResponse` 改为 `record`（保留 `CommonResult` 双参便捷构造器，删除可变无参构造器与 `@Data`）；`TestServiceImpl.testConfig()` 改用 Text Block + `formatted()`；测试侧访问器同步为 `code()/msg()/data()/version()/projectName()`；Jackson 3.x 对 Record 的序列化（`/version`、`/read-config`）与反序列化（`testLoadConfig`）经 JUnit 49/49 验证，API 契约不变

- [x] **Q-P2-3**：移除冗余的注释代码
  - `JsonUtil.java` 中注释掉的 import 语句应删除
  - ✅ 已修复（2026-08-19）：删除 `JsonUtil.java` 中注释掉的 `// import tools.jackson.datatype.jsr310.JavaTimeModule;`（Jackson 3.x 已废弃，仅作对比），顺带清理 package 后多余空行、调整 `java.util` import 字母序；全项目 grep 确认注释 import 零残留，28 源文件编译通过

- [x] **Q-P2-4**：Controller 中嵌套的内部类提取为独立类
  - `TestController.VersionResponse` 应提取为独立文件
  - ✅ 已修复（2026-08-19）：新建 `com.my.work.model.VersionResponse`（与 `CommonResult` 同包，@Data 风格一致），`TestController` 删除内部类并改为 import，顺带移除不再使用的 `lombok.Data` import；29 源文件编译通过（0 errors）

- [x] **Q-P2-5**：`@RequestMapping` 路径风格统一
  - 当前混用 `/get`（短横线风格不一致）和 `/daily-task`（短横线风格）
  - ✅ 已修复（2026-08-19）：统一为 kebab-case（多词路径短横线分隔）——`/saveconfig`→`/save-config`、`/readconfig`→`/read-config`、`/savelog`→`/save-log`、`/otherinfo`→`/other-info`；单字路径（`/get`、`/version` 等）不变，`/daily-task` 已合规；Javadoc/日志/readme 接口文档同步；29 源文件编译通过（0 errors）

### 性能-P2

- [x] **PERF-P2-1**：`ECCCrypto` 每次加解密都注册 Provider
  - `static` 块已优化，但可考虑使用 `@Bean` 方式注册一次
  - ✅ 已修复（2026-08-24）：新增 `config/SecurityConfig.java`（`@Configuration` + `@Bean` 注册 `BouncyCastleProvider`，Spring 单例语义保证只注册一次）；移除 `ECCCrypto` 与 `ECCKeyReader` 中的 `static` 块，改为 `ensureProvider()` 惰性兜底（非 Spring 环境如单元测试首次调用时自动注册，Spring 环境下为 no-op）；Javadoc 补全；javac 30 源文件编译通过，JUnit 49/49 通过（含 ECC 加解密 roundtrip 验证兜底生效）
- [x] **PERF-P2-2**：避免在循环中创建对象
  - `TestServiceImpl.test()` 中 JSON 解析可在提取为独立方法
  - ✅ 已修复（2026-08-24）：将 `test()` 中 for 循环内 20 行 JSON 解析逻辑提取为 `private void parseAndLogJsonResult(Map<String, Object>)` 独立方法（含完整 Javadoc），`test()` 方法职责更清晰；javac 30 源文件编译通过，JUnit 49/49 通过

### 文档-P2

- [x] **DOC-P2-1**：README 中的接口文档保持更新
  - 新增/修改接口时同步更新
  - ✅ 已修复（2026-08-24）：对照 `TestController` 13 个端点逐项核对 README「使用示例」——补齐遗漏的 `GET /test/config`（测试默认配置接口，含表格与 curl 示例）；「功能特性」表格同步补充 `/test/config` 验证说明；修复定时任务章节过时示例代码（`log.info("...：" + result)` 字符串拼接 → `log.info("...：{}", result)` 占位符，与 Q-P1-3 实际代码一致）；项目结构树补充 `exception/GlobalExceptionHandler.java` 与 `config/SecurityConfig.java`（PERF-P2-1 新增），并修正 util 目录树未闭合格式
- [x] **DOC-P2-2**：数据库变更同步更新 SQL 脚本文档
  - ✅ 已修复（2026-08-24）：新建 `sql/init.sql`（2 表 + 8 函数，从 README 内嵌脚本提取并收敛为**单一事实来源**，幂等可重复执行，函数头部标注 Mapper 调用点）与 `sql/README.md`（脚本清单/执行方式/**数据库变更三步工作流**/命名约定）；README「数据库初始化」章节由内嵌 8 段完整脚本重构为对象清单表 + 执行方式 + 变更规范；安装步骤改为 `psql -f sql/init.sql`；贡献指南同步更新；项目结构树补 `sql/` 节点。消除「README 内嵌脚本多副本不同步」风险
- [x] **DOC-P2-3**：使用 Swagger/OpenAPI 自动生成接口文档
  - ✅ 已修复（2026-08-24）：引入 `springdoc-openapi-starter-webmvc-ui` **3.0.3**（Spring Boot 4 兼容线，Jackson 3 迁移完成，官方 demo 验证）；新建 `config/OpenApiConfig.java`（`@OpenAPIDefinition` 标题/版本/描述 + `GroupedOpenApi` 分组 `public-api`）；`TestController` 类级 `@Tag` + 13 个端点 `@Operation(summary)` 中文摘要；`CommonResult`/`VersionResponse`/`SaveConfigRequest` 加 `@Schema` 字段描述；`application.yml` 显式开启 `springdoc.api-docs.enabled=true` 与 `springdoc.swagger-ui.enabled=true`（Spring Boot 4 必需，且需配 `path`）；文档地址：`/swagger-ui.html`（交互 UI）、`/v3/api-docs`（OpenAPI JSON）、`/v3/api-docs.yaml`（YAML）；javac 31 源文件编译通过（0 errors），JUnit 49/49 通过，注解字节码验证写入（@Operation×13/@Schema×3 类）

---

## 附：Review 流程

```
PR 提交
  │
  ▼
┌─────────────────────────┐
│ 1. 自动化检查            │
│   - mvn clean compile    │
│   - 编译通过？           │
└────────┬────────────────┘
         │ 通过
         ▼
┌─────────────────────────┐
│ 2. P0 检查清单           │
│   - 安全 / 异常 / 功能   │
│   - 全部通过？           │
└────────┬────────────────┘
         │ 通过
         ▼
┌─────────────────────────┐
│ 3. P1 检查清单           │
│   - 代码质量 / 可维护性  │
│   - 记录待改进项          │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ 4. P2 检查清单           │
│   - 精进建议（可选）      │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ 5. 审查结论              │
│   - Approve             │
│   - Request Changes     │
│   - Comment             │
└─────────────────────────┘
```

---

## 附：当前项目 Review 总结

基于对全项目代码的审查，以下是按优先级排列的改进项汇总：

### P0（立即修复）

| 编号 | 文件 | 问题 | 修正建议 | 状态 |
|------|------|------|---------|------|
| 1 | ~~`AesEcbUtils.java`~~ | 硬编码密钥 + ECB 模式 | 删除旧类，新增 `AesGcmUtils`（GCM 模式、随机 IV、密钥由调用方传入） | ✅ 已修复（2026-08-19） |
| 2 | ~~`AesUtil.java`~~ | 硬编码静态 IV | 随机 IV 并随密文返回 `Base64(IV+ciphertext)`，密钥由调用方传入 | ✅ 已修复（2026-08-19） |
| 3 | ~~`TestController.java`~~ | 异常信息返回客户端 | 新增 `GlobalExceptionHandler`（`@RestControllerAdvice`）统一兜底，移除 `return e.getMessage()` | ✅ 已修复（2026-08-19） |
| 4 | ~~`TestServiceImpl.java`~~ | `testLoadConfig` 异常返回 null | 移除 catch，异常上抛；接口与 Controller 同步声明 `throws Exception` | ✅ 已修复（2026-08-19） |
| 5 | ~~`TestServiceImpl.java`~~ | `health()` 暴露堆栈 | 移除 `stackTrace` 与 `e.getMessage()`，堆栈改 `log.error`，仅返回 `status=DOWN` + 通用提示 | ✅ 已修复（2026-08-19） |
| 6 | ~~`AppleClientSecret.java`~~ | 硬编码 EC P-256 私钥常量 `PRIVATE_KEY_256`（漏检项，类无调用点，`createClientSecret` 已通过参数接收私钥） | 整类删除（与 `DesEncryptor` 同款处理），pom 中 `java-jwt` 4.4.0 同时成为无用依赖（可后续清理） | ✅ 已修复（2026-08-19） |

### P1（尽快修复）

| 编号 | 文件 | 问题 | 修正建议 | 状态 |
|------|------|------|---------|------|
| 6 | ~~多个文件~~ | 字段注入 → 构造器注入 | 使用 `@RequiredArgsConstructor` | ✅ 已修复（2026-08-19，3 文件 7 处） |
| 7 | ~~多个文件~~ | `System.out.println` | 改用 SLF4J / 删除 main 自测代码 | ✅ 已修复（2026-08-19，P0-4 清 `TestController` 3 处 + Q-P1-2 清 `Application`/`PemUtils`/6 个工具类 main 共 19 处，全项目 0 残留） |
| 8 | ~~多个文件~~ | 日志字符串拼接 | 改用 `{}` 占位符 | ✅ 已修复（2026-08-19，2 文件 2 处） |
| 9 | ~~`TestServiceImpl.java`~~ | 缺少 `@Override` | 补充注解 | ✅ 已修复（2026-08-19，`TestServiceImpl` 5 处 + `ClientBServiceImpl` 1 处） |
| 10 | ~~`TestMapper.java`~~ | 方法名 `test_sum_array` | 改为 `testSumArray` | ✅ 已修复（2026-08-19，PG 函数名保留） |
| 11 | ~~`TestController.java`~~ | `@RequestMapping` 通用注解 | 改用具体 HTTP 方法注解 | ✅ 已修复（2026-08-19，3 处替换，`/saveconfig` 按语义改 POST） |
| 12 | ~~`DesEncryptor.java`~~ | 使用 DES | 改用 AES | ✅ 已修复（2026-08-19，整类删除：无调用点，弱算法+硬编码密钥，pgcrypto 兼容用 SQL 侧实现） |
| 13 | ~~全项目~~ | 无测试代码 | 补充核心模块单元测试 | ✅ 已修复（2026-08-19，pom 加 `spring-boot-starter-test`，新增 7 个工具类 JUnit 测试共 19 用例） |
| 14 | ~~全项目~~ | 无全局异常处理 | 新增 `@RestControllerAdvice` | ✅ 已修复（2026-08-19，`GlobalExceptionHandler`） |
| 15 | ~~工具类~~ | 工具类未声明 `final` + 私有构造 | 补齐 8 个工具类 `public final class` + 私有构造 | ✅ 已修复（2026-08-19，6 个工具类本次补齐 + 2 个已修） |
| 16 | ~~`TestController.java`/`GlobalExceptionHandler.java`~~ | API 响应格式不统一（String/Map/VersionResponse 混用） | 13 个接口统一返回 `CommonResult`，异常处理器同步返回 `ResponseEntity<CommonResult>` | ✅ 已修复（2026-08-19，javac 编译 28 源文件通过） |
| 17 | ~~`pom.xml`~~ | Lombok 1.18.8 与 JDK 21 不兼容（`IllegalAccessError`，BOM 默认版本） | pom `properties` 新增 `<lombok.version>1.18.34</lombok.version>` 覆盖 Spring Boot BOM | ✅ 已修复（2026-08-19，`mvn -DskipTests compile` BUILD SUCCESS，`dependency:list` 确认 Lombok 1.18.34 生效） |
| 18 | ~~`Md5Util.java`~~ | MD5 不应用于密码哈希或签名 | 评估并加安全警示 Javadoc | ✅ 已评估（2026-08-20，SEC-P1-2：生产零调用点，仅通用摘要，未用于密码/签名；类与方法 Javadoc 已加禁止用途说明） |
| 19 | ~~`TestController.java`~~ | 接口输入参数无校验 | 松散参数封装 DTO + `@Valid` | ✅ 已修复（2026-08-20，SEC-P1-3：新建 `SaveConfigRequest` DTO；Controller 类级 `@Validated` + 3 处 `@RequestBody String` 参数级 `@NotBlank`；`GlobalExceptionHandler` 补 6 类参数绑定/校验异常统一 400；新增 `SaveConfigRequestTest` 4 用例，JUnit 23/23） |
| 20 | ~~`TestController.java`~~ | Controller 包含业务逻辑（密钥读取 / MANIFEST 解析） | 下沉至 Service 层 | ✅ 已修复（2026-08-20，M-P1-1：`getVersion`/`encrypt`/`decrypt` 下沉至 `TestService`/`TestServiceImpl`，Controller 仅保留 HTTP 层调用，`configData` 依赖移除；新增 `TestServiceImplTest` 4 用例，JUnit 27/27） |
| 21 | ~~全项目~~ | public 方法缺少 Javadoc（`@param`/`@return`/`@throws` 缺失或为空） | 补齐全部 public 方法 Javadoc | ✅ 已修复（2026-08-20，M-P1-5：65 个 public 方法/构造器全部具备完整 Javadoc，脚本校验 `@param`/`@return`/`@throws` 与签名一致；补齐 13 处缺失 + 15 处空标签 + 10 处 `@throws`；javac 29 源文件编译通过，JUnit 27/27） |

### P2（后续优化）

| 编号 | 文件 | 问题 | 修正建议 | 状态 |
|------|------|------|---------|------|
| 15 | ~~`RsaUtil.java` 等~~ | main 方法测试代码 | 迁移到单元测试 | ✅ 已修复（2026-08-19，6 个工具类 main 随 Q-P1-2 删除；7 个 JUnit 测试类随 Q-P2-1 补齐） |
| 16 | ~~`TestController.java`~~ | 内部类 `VersionResponse` | 提取为独立文件 | ✅ 已修复（2026-08-19，Q-P2-4，提取至 `com.my.work.model.VersionResponse`） |
| 17 | ~~`JsonUtil.java`~~ | 注释掉的 import | 删除 | ✅ 已修复（2026-08-19，Q-P2-3，删除废弃 `JavaTimeModule` 注释 import，全项目零残留） |
| 18 | ~~工具类~~ | 未声明 `final` + 私有构造 | 补充 | ✅ 已修复（2026-08-19，M-P1-3，8 个工具类全部 final + 私有构造） |
| 19 | ~~`ConfigData.java`~~ | 部分配置项无注释 | 补充 Javadoc | ✅ 已修复（2026-08-19） |
