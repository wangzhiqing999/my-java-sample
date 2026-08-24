package com.my.work.service;

import com.my.work.config.ConfigData;
import com.my.work.mapper.TestMapper;
import com.my.work.model.CommonResult;
import com.my.work.service.impl.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestServiceImpl 核心方法单元测试（TEST-P1-2）.
 *
 * <p>覆盖 {@code test()}/{@code health()}/{@code dailyTask()}/{@code testConfig()}/
 * {@code testSaveConfig()}/{@code testLoadConfig()}/{@code saveLogData()}，
 * 与 TestServiceImplTest（加解密 roundtrip）互补。
 * Mapper 用 JDK 动态代理桩替代（无 mockito），按方法名返回预置数据并记录调用次数。
 */
class TestServiceImplCoreTest {

    /** 演示私钥（与 application.yml my.work.config.privateKeyPem 一致）. */
    private static final String PRIVATE_KEY_PEM = "-----BEGIN EC PRIVATE KEY-----\n"
            + "MHQCAQEEIFUmcJTzQ59/1Am2RS1wdYmPvyVTW9vTBIfXkBYRE/VSoAcGBSuBBAAK\n"
            + "oUQDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazEKPiJ09yf6cVlnf73YgYlWzV0\n"
            + "4RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==\n"
            + "-----END EC PRIVATE KEY-----\n";

    /** 演示公钥（与 application.yml my.work.config.publicKeyPem 一致）. */
    private static final String PUBLIC_KEY_PEM = "-----BEGIN PUBLIC KEY-----\n"
            + "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazE\n"
            + "KPiJ09yf6cVlnf73YgYlWzV04RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==\n"
            + "-----END PUBLIC KEY-----\n";

    /** Mapper 桩方法调用计数（key=方法名）. */
    private final Map<String, AtomicInteger> mapperCalls = new HashMap<>();

    /** 捕获 fn_save_config 的第一个参数（配置编码），用于验证透传. */
    private final AtomicReference<String> savedConfigCode = new AtomicReference<>();

    /** 控制 selectTest 是否抛异常（健康检查 DOWN 路径）. */
    private boolean dbDown;

    /** 控制 fn_save_config 是否抛异常（FN-P0-1 异常上抛用例）. */
    private boolean saveConfigThrows;

    private TestServiceImpl service;

    @BeforeEach
    void setUp() {
        mapperCalls.clear();
        savedConfigCode.set(null);
        dbDown = false;
        saveConfigThrows = false;

        TestMapper mapper = (TestMapper) Proxy.newProxyInstance(
                TestMapper.class.getClassLoader(),
                new Class<?>[]{TestMapper.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectMethod(proxy, method, args);
                    }
                    mapperCalls.computeIfAbsent(method.getName(), k -> new AtomicInteger()).incrementAndGet();
                    switch (method.getName()) {
                        case "selectTest":
                            if (dbDown) {
                                throw new RuntimeException("mock db down");
                            }
                            return 1;
                        case "selectFunction":
                            return "PostgreSQL 16.0";
                        case "callTestNopNor":
                        case "callTestHavepNor":
                            return null;
                        case "testHavepHaver":
                            return 100L;
                        case "testHavepHaverj":
                        case "testHavepjHaverj": {
                            Map<String, Object> json = new HashMap<>();
                            json.put("result", "{\"code\":200,\"msg\":\"ok\",\"id\":1}");
                            return json;
                        }
                        case "testSumArray":
                            return 10;
                        case "fn_save_config":
                            if (saveConfigThrows) {
                                throw new RuntimeException("mock db write failure");
                            }
                            savedConfigCode.set((String) args[0]);
                            return null;
                        case "fn_get_config":
                            return "{\"code\":200,\"msg\":\"ok\",\"data\":\"cfg\"}";
                        default:
                            return null;
                    }
                });

        ConfigData configData = new ConfigData();
        configData.setPrivateKeyPem(PRIVATE_KEY_PEM);
        configData.setPublicKeyPem(PUBLIC_KEY_PEM);

        service = new TestServiceImpl(mapper, configData);
    }

    /** JDK Proxy 必须放行的 Object 方法. */
    private Object objectMethod(Object proxy, Method method, Object[] args) {
        switch (method.getName()) {
            case "toString":
                return proxy.getClass().getSimpleName() + "@"
                        + Integer.toHexString(System.identityHashCode(proxy));
            case "hashCode":
                return System.identityHashCode(proxy);
            case "equals":
                return proxy == args[0];
            default:
                return null;
        }
    }

    private int mapperCallCount(String methodName) {
        AtomicInteger count = mapperCalls.get(methodName);
        return count == null ? 0 : count.get();
    }

    @Test
    void test_完整存储过程调用链无异常() {
        service.test();

        // 关键调用点均被触达
        assertTrue(mapperCallCount("selectTest") >= 1, "selectTest 应被调用");
        assertTrue(mapperCallCount("selectFunction") >= 1, "selectFunction 应被调用");
        assertTrue(mapperCallCount("callTestNopNor") >= 1, "callTestNopNor 应被调用");
        assertTrue(mapperCallCount("callTestHavepNor") >= 1, "callTestHavepNor 应被调用");
        assertTrue(mapperCallCount("testHavepHaver") >= 1, "testHavepHaver 应被调用");
        assertTrue(mapperCallCount("testHavepHaverj") >= 1, "testHavepHaverj 应被调用");
        assertTrue(mapperCallCount("testHavepjHaverj") >= 1, "testHavepjHaverj 应被调用");
        assertTrue(mapperCallCount("testSumArray") >= 1, "testSumArray 应被调用");
    }

    @Test
    void health_数据库正常返回UP() {
        Map<String, Object> result = service.health();

        assertEquals("UP", result.get("status"));
        assertEquals("health", result.get("message"));
    }

    @Test
    void health_数据库异常返回DOWN() {
        dbDown = true;

        Map<String, Object> result = service.health();

        assertEquals("DOWN", result.get("status"));
        assertNotNull(result.get("message"));
    }

    @Test
    void testConfig_返回默认配置拼接结果() {
        String result = service.testConfig();

        assertTrue(result.contains("testBooleanDefaultValue = true"), "应包含布尔默认值");
        assertTrue(result.contains("testIntDefaultValue = 1024"), "应包含整型默认值");
        assertTrue(result.contains("testStringDefaultValue = Default String value"), "应包含字符串默认值");
    }

    @Test
    void dailyTask_返回任务执行成功() {
        assertEquals("任务执行成功", service.dailyTask());
    }

    @Test
    void saveLogData_解密后调用存储过程() throws Exception {
        String cipherText = service.encrypt("{\"log_text\":\"这是一条测试日志\"}");

        service.saveLogData(cipherText);

        assertTrue(mapperCallCount("testHavepjHaverj") >= 1, "解密后应调用 testHavepjHaverj 入库");
    }

    @Test
    void testSaveConfig_序列化后调用fn_save_config() throws Exception {
        CommonResult data = new CommonResult(1, "test_message");

        service.testSaveConfig("TEST", data);

        assertEquals(1, mapperCallCount("fn_save_config"), "fn_save_config 应被调用 1 次");
        assertEquals("TEST", savedConfigCode.get(), "配置编码应透传给 Mapper");
    }

    @Test
    void testSaveConfig_数据库写入失败时异常上抛不吞异常() {
        saveConfigThrows = true;

        CommonResult data = new CommonResult(1, "test_message");

        assertThrows(RuntimeException.class, () -> service.testSaveConfig("TEST", data),
                "FN-P0-1：数据库写入异常必须上抛，禁止吞异常后静默返回");
    }

    @Test
    void testLoadConfig_解析配置JSON为CommonResult() throws Exception {
        CommonResult result = service.testLoadConfig("TEST");

        assertNotNull(result);
        assertEquals(200, result.code());
        assertEquals("ok", result.msg());
        assertEquals("cfg", result.data());
        assertEquals(1, mapperCallCount("fn_get_config"));
    }
}
