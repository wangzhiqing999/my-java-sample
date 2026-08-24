-- ============================================================================
-- test-service 数据库初始化脚本
-- ============================================================================
-- 适用数据库 : PostgreSQL 12+
-- 脚本用途   : 创建项目依赖的全部表结构与 PostgreSQL 函数
-- 维护规范   : 本文件是数据库对象的【单一事实来源】。任何数据库变更
--             （新增/修改表、函数、索引等）必须同步更新本文件，
--             并保持与 src/main/java/com/my/work/mapper/TestMapper.java
--             中的 SQL 调用一致。具体流程见 sql/README.md。
-- 执行方式   : psql -h <host> -p 5432 -U <user> -d <database> -f sql/init.sql
-- 幂等性     : 表使用 CREATE TABLE IF NOT EXISTS，函数使用 CREATE OR REPLACE，
--             可重复执行，不会因重复执行而报错。
-- ============================================================================

BEGIN;

-- ----------------------------------------------------------------------------
-- 1. 测试日志表
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS test_log (
    log_id serial4 NOT NULL,                -- 日志流水号，自增主键
    log_text text NULL,                     -- 日志文本信息
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,  -- 日志创建时间
    CONSTRAINT test_log_pkey PRIMARY KEY (log_id)
);
COMMENT ON TABLE public.test_log IS '测试日志的表';
COMMENT ON COLUMN public.test_log.log_id IS '日志流水号，自增主键';
COMMENT ON COLUMN public.test_log.log_text IS '日志文本信息';
COMMENT ON COLUMN public.test_log.created_at IS '日志创建的时间，默认为当前时间';

-- ----------------------------------------------------------------------------
-- 2. 通用配置表
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS common_config (
    config_code   VARCHAR(32) NOT NULL,
    config_desc   VARCHAR(256),
    config_value  JSON,
    ctime timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    utime timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT common_config_pkey PRIMARY KEY (config_code)
);
COMMENT ON TABLE common_config IS '通用配置表';
COMMENT ON COLUMN common_config.config_code IS '配置代码';
COMMENT ON COLUMN common_config.config_desc IS '配置描述';
COMMENT ON COLUMN common_config.config_value IS '配置值';
COMMENT ON COLUMN common_config.ctime IS '配置创建时间';
COMMENT ON COLUMN common_config.utime IS '配置更新时间';

-- ----------------------------------------------------------------------------
-- 3. 无参数、无返回值函数
--    Mapper 调用点：TestMapper.callTestNopNor()
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_nop_nor()
RETURNS void
LANGUAGE plpgsql
AS $function$
BEGIN
    INSERT INTO test_log(log_text, created_at)
    VALUES('无参数，无返回', CURRENT_TIMESTAMP);
END;
$function$;

-- ----------------------------------------------------------------------------
-- 4. 有参数、无返回值函数
--    Mapper 调用点：TestMapper.callTestHavepNor(#{logText})
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_havep_nor(p_log_text character varying)
RETURNS void
LANGUAGE plpgsql
AS $function$
BEGIN
    INSERT INTO test_log(log_text, created_at)
    VALUES('有参数，无返回' || p_log_text, CURRENT_TIMESTAMP);
END;
$function$;

-- ----------------------------------------------------------------------------
-- 5. 有参数、有返回值（bigint）函数
--    Mapper 调用点：TestMapper.testHavepHaver(#{logText})
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_havep_haver(p_log_text character varying)
RETURNS bigint
LANGUAGE plpgsql
AS $function$
DECLARE
    new_id bigint;
BEGIN
    INSERT INTO test_log(log_text, created_at)
    VALUES('有参数，有返回：' || p_log_text, CURRENT_TIMESTAMP)
    RETURNING log_id INTO new_id;
    RETURN new_id;
END;
$function$;

-- ----------------------------------------------------------------------------
-- 6. 有参数、有返回值，返回类型为 JSON 的函数
--    Mapper 调用点：TestMapper.testHavepHaverj(#{logText})
--    返回 JSON 结构：{"code": 0, "msg": "success", "id": <新记录ID>}
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_havep_haverj(p_log_text character varying)
RETURNS json
LANGUAGE plpgsql
AS $function$
DECLARE
    new_id bigint;
BEGIN
    INSERT INTO test_log(log_text, created_at)
    VALUES('有参数，返回JSON：' || p_log_text, CURRENT_TIMESTAMP)
    RETURNING log_id INTO new_id;

    RETURN json_build_object(
        'code', 0,
        'msg',   'success',
        'id',    new_id);
END;
$function$;

-- ----------------------------------------------------------------------------
-- 7. 参数与返回值均为 JSON 的函数
--    Mapper 调用点：TestMapper.testHavepjHaverj(#{logData}::json)
--    入参 JSON 结构：{"log_text": "..."}；返回结构同第 6 项
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_havepj_haverj(p_log_data json)
RETURNS json
LANGUAGE plpgsql
AS $function$
DECLARE
    new_id bigint;
    v_log_text varchar;
BEGIN
    v_log_text := p_log_data->>'log_text';

    INSERT INTO test_log(log_text, created_at)
    VALUES('参数JSON，返回JSON：' || v_log_text, CURRENT_TIMESTAMP)
    RETURNING log_id INTO new_id;

    RETURN json_build_object(
        'code', 0,
        'msg',   'success',
        'id',    new_id);
END;
$function$;

-- ----------------------------------------------------------------------------
-- 8. 数组求和函数
--    Mapper 调用点：TestMapper.testSumArray(#{datas})
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.test_sum_array(p_datas INT[])
RETURNS INT
LANGUAGE plpgsql
AS $function$
DECLARE
    i INT;
    v_result INT;
BEGIN
    v_result := 0;
    FOR i IN 1..array_length(p_datas, 1) LOOP
        v_result := v_result + p_datas[i];
    END LOOP;
    RETURN v_result;
END;
$function$;

-- ----------------------------------------------------------------------------
-- 9. 配置保存函数（UPSERT 语义）
--    Mapper 调用点：TestMapper.fn_save_config(#{p_code}, #{p_value}::json)
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_save_config(p_code VARCHAR(32), p_value JSON)
RETURNS void
LANGUAGE plpgsql
AS $function$
BEGIN
    INSERT INTO common_config (config_code, config_desc, config_value)
    VALUES (p_code, '-', p_value)
    ON CONFLICT (config_code)
    DO UPDATE SET
        config_value = p_value,
        utime = CURRENT_TIMESTAMP;
END;
$function$;

-- ----------------------------------------------------------------------------
-- 10. 配置查询函数
--     Mapper 调用点：TestMapper.fn_get_config(#{p_code})
--     无匹配记录时返回 NULL
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_get_config(p_code VARCHAR(32))
RETURNS JSON
LANGUAGE plpgsql
AS $function$
DECLARE
    v_result JSON;
BEGIN
    SELECT config_value INTO v_result
    FROM common_config
    WHERE config_code = p_code;
    RETURN v_result;
END;
$function$;

COMMIT;
