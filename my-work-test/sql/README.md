# SQL 脚本目录

本目录存放 test-service 项目依赖的全部 PostgreSQL 数据库对象脚本，是数据库结构的**单一事实来源**。

## 脚本清单

| 文件 | 用途 | 内容 |
|------|------|------|
| `init.sql` | 数据库初始化（可重复执行） | `test_log` 表、`common_config` 表、8 个 PostgreSQL 函数 |

## 执行方式

```bash
psql -h <host> -p 5432 -U <user> -d <database> -f sql/init.sql
```

脚本具有幂等性（表 `CREATE TABLE IF NOT EXISTS`、函数 `CREATE OR REPLACE`），可安全重复执行。

## 数据库变更工作流

任何数据库结构变更必须遵循以下流程，**禁止只改代码不改脚本，或只改脚本不改文档**：

1. **修改 Mapper**：在 `src/main/java/com/my/work/mapper/TestMapper.java` 中新增/修改 SQL 调用
2. **同步 `init.sql`**：在 `sql/init.sql` 中新增/修改对应的表或函数定义，并在函数头部注释中补充 Mapper 调用点
3. **更新 README**：同步更新项目根目录 `readme.md` 的「数据库初始化」章节中的对象清单表
4. **验证**：在目标 PostgreSQL 库执行 `init.sql`，确认无报错且函数行为符合预期

## 命名约定

- 表名：小写蛇形（`test_log`、`common_config`）
- 函数名：小写蛇形（`test_nop_nor`、`fn_save_config`）
- 参数：`p_` 前缀（如 `p_log_text`），语义化命名
- 返回值：有返回值的函数返回 `bigint`/`int`/`json`，无返回值的返回 `void`
- 函数内嵌 `COMMENT ON` 注释表与列含义
