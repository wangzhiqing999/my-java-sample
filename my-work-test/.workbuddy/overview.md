# FN-P0-1 核心业务路径异常处理概览

## 审查结论

对全项目核心业务路径（数据库操作 / 加解密 / 外部调用 / 定时任务）逐项审查，定位 **1 个 P0 级缺陷**：

| 路径 | 位置 | 审查结果 |
|------|------|---------|
| **数据库写入（保存配置）** | `TestServiceImpl.testSaveConfig()` | ❌ **吞掉异常**：`catch` 后仅记日志静默返回，Controller 仍回 200 success，客户端误判保存成功 |
| 数据库读（`test()` 全链路 8 调用） | `TestServiceImpl.test()` | ✅ 异常上抛，`GlobalExceptionHandler` 兜底 500（补 Javadoc 声明意图） |
| 数据库读/写（配置/日志） | `testLoadConfig()` / `saveLogData()` | ✅ `throws Exception` |
| 加解密 | `encrypt()`/`decrypt()` + `ECCCrypto`/`ECCKeyReader`/`PemUtils` | ✅ `throws Exception` |
| 健康检查 | `health()` | ✅ try-catch 返回 DOWN（降级语义，不暴露异常） |
| MANIFEST 读取 | `getVersion()` | ✅ catch 返回默认值（降级语义） |
| 外部调用 / 定时任务 | Client 实现 / `TestScheduledTask` | ✅ 纯内存/无 IO，无风险 |

## 修复内容

1. **`testSaveConfig` 吞异常 → 异常上抛**（违反 E-06 规范）
   - `TestService.testSaveConfig` 签名加 `throws Exception`，Javadoc 补 `@throws`
   - `TestServiceImpl.testSaveConfig` 移除 try-catch，异常上抛（与 `testLoadConfig`/`saveLogData` 一致）
   - `TestController.saveConfig` 加 `throws Exception`，异常穿透至 `GlobalExceptionHandler` → 500 + 通用消息
2. **`test()` Javadoc 强化**：接口 + 实现补 `@throws RuntimeException` 说明（MyBatis 运行时异常无需 checked 声明，链路完整）
3. **回归测试 +2**（49 → 51）：
   - `TestServiceImplCoreTest.testSaveConfig_数据库写入失败时异常上抛不吞异常`
   - `TestControllerTest.saveConfig_service抛异常时Controller不吞异常直接上抛`
   - 现有 2 个成功路径用例方法签名适配 `throws Exception`

## 验证

- javac 31 源文件 0 errors（仅既有 deprecation）
- **JUnit 51/51 通过**（14 containers）

## 变更文件

| 文件 | 操作 |
|------|------|
| `service/TestService.java` | 修改（`testSaveConfig` + `throws Exception`；`test()` Javadoc `@throws`） |
| `service/impl/TestServiceImpl.java` | 修改（移除 try-catch 吞异常；`test()` Javadoc `@throws`） |
| `controller/TestController.java` | 修改（`saveConfig` + `throws Exception`） |
| `test/.../TestServiceImplCoreTest.java` | 修改（+1 异常上抛用例 + 签名适配） |
| `test/.../TestControllerTest.java` | 修改（+1 异常穿透用例 + 签名适配） |
| `CODE_STANDARDS_AND_REVIEW_CHECKLIST.md` | 更新（FN-P0-1 ✅） |

## 下一步

- P0 剩余未评估：**FN-P0-2（事务边界）/ FN-P0-3（资源关闭）**
- 其余 P0（安全/异常）、P1、P2 已全部清零
