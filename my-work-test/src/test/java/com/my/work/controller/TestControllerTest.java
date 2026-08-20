package com.my.work.controller;

import com.my.work.model.CommonResult;
import com.my.work.model.SaveConfigRequest;
import com.my.work.model.VersionResponse;
import com.my.work.service.ClientService;
import com.my.work.service.OtherClientService;
import com.my.work.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestController 集成测试（TEST-P1-3）.
 *
 * <p>本机无 spring-test / MockMvc / mockito，采用 JDK 动态代理桩替代三个 Service 依赖，
 * 直接实例化 Controller 并调用全部端点方法，验证：
 * <ul>
 *   <li>HTTP 层行为：成功响应统一为 {@code code=200, msg=success}</li>
 *   <li>数据包装：字符串/对象/Map 分别正确放入 data</li>
 *   <li>依赖编排：正确的 Service 方法被调用（调用计数）</li>
 *   <li>异常路径：Service 抛异常时 Controller 不吞异常、直接上抛</li>
 * </ul>
 *
 * <p>注意：{@code @Valid}/{@code @RequestBody} 等 Spring MVC 机制不在本测试范围
 * （Bean Validation 已由 SaveConfigRequestTest 覆盖），本测试专注 Controller 的 HTTP 层编排逻辑。
 */
class TestControllerTest {

    /** TestService 桩方法调用计数（key=方法名）. */
    private final Map<String, AtomicInteger> testServiceCalls = new HashMap<>();

    /** 控制桩 encrypt 是否抛异常（异常路径用例）. */
    private boolean encryptThrows;

    private TestController controller;

    @BeforeEach
    void setUp() {
        testServiceCalls.clear();
        encryptThrows = false;

        TestService testService = (TestService) Proxy.newProxyInstance(
                TestService.class.getClassLoader(),
                new Class<?>[]{TestService.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectMethod(proxy, method, args);
                    }
                    testServiceCalls.computeIfAbsent(method.getName(), k -> new AtomicInteger()).incrementAndGet();
                    switch (method.getName()) {
                        case "test":
                        case "saveLogData":
                        case "testSaveConfig":
                            return null;
                        case "health": {
                            Map<String, Object> health = new HashMap<>();
                            health.put("status", "UP");
                            health.put("message", "health");
                            return health;
                        }
                        case "dailyTask":
                            return "任务执行成功";
                        case "testConfig":
                            return "config-string";
                        case "testLoadConfig":
                            return new CommonResult(200, "ok", "cfg");
                        case "getVersion":
                            return new VersionResponse("1.0.0", "test-service");
                        case "encrypt":
                            if (encryptThrows) {
                                throw new RuntimeException("mock encrypt failure");
                            }
                            return "cipher:" + args[0];
                        case "decrypt":
                            return "plain:" + args[0];
                        default:
                            return null;
                    }
                });

        ClientService clientService = (ClientService) Proxy.newProxyInstance(
                ClientService.class.getClassLoader(),
                new Class<?>[]{ClientService.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectMethod(proxy, method, args);
                    }
                    switch (method.getName()) {
                        case "getClientInfo":
                            return "客户A";
                        case "getTodoList":
                            return List.of("todo-a1", "todo-a2");
                        default:
                            return null;
                    }
                });

        OtherClientService otherClientService = (OtherClientService) Proxy.newProxyInstance(
                OtherClientService.class.getClassLoader(),
                new Class<?>[]{OtherClientService.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectMethod(proxy, method, args);
                    }
                    switch (method.getName()) {
                        case "getClientInfo":
                            return "客户C";
                        case "getTodoList":
                            return List.of("todo-c1");
                        default:
                            return null;
                    }
                });

        controller = new TestController(testService, clientService, otherClientService);
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

    private void assertSuccess(CommonResult result) {
        assertEquals(200, result.getCode(), "成功响应 code 应为 200");
        assertEquals("success", result.getMsg(), "成功响应 msg 应为 success");
    }

    private int callCount(String methodName) {
        AtomicInteger count = testServiceCalls.get(methodName);
        return count == null ? 0 : count.get();
    }

    @Test
    void get_调用testService的test方法并返回成功() {
        CommonResult result = controller.get();

        assertSuccess(result);
        assertNull(result.getData());
        assertEquals(1, callCount("test"));
    }

    @Test
    void getVersion_data为版本信息的JSON序列化() {
        CommonResult result = controller.getVersion();

        assertSuccess(result);
        assertTrue(result.getData() != null && result.getData().contains("projectName"),
                "data 应包含版本对象序列化字段");
        assertTrue(result.getData().contains("test-service"));
        assertEquals(1, callCount("getVersion"));
    }

    @Test
    void health_data为健康状态的JSON序列化() {
        CommonResult result = controller.health();

        assertSuccess(result);
        assertTrue(result.getData() != null && result.getData().contains("UP"),
                "data 应包含健康状态 UP");
        assertEquals(1, callCount("health"));
    }

    @Test
    void saveConfig_调用testSaveConfig并返回成功() {
        SaveConfigRequest request = new SaveConfigRequest();
        request.setCode(1);
        request.setMsg("test_message");

        CommonResult result = controller.saveConfig(request);

        assertSuccess(result);
        assertNull(result.getData());
        assertEquals(1, callCount("testSaveConfig"));
    }

    @Test
    void readConfig_data为配置对象的JSON序列化() throws Exception {
        CommonResult result = controller.readConfig();

        assertSuccess(result);
        assertTrue(result.getData() != null && result.getData().contains("cfg"),
                "data 应包含桩返回的配置对象序列化内容");
        assertEquals(1, callCount("testLoadConfig"));
    }

    @Test
    void encrypt_data为加密结果() throws Exception {
        CommonResult result = controller.encrypt("hello");

        assertSuccess(result);
        assertEquals("cipher:hello", result.getData());
        assertEquals(1, callCount("encrypt"));
    }

    @Test
    void decrypt_data为解密结果() throws Exception {
        CommonResult result = controller.decrypt("cipher-text");

        assertSuccess(result);
        assertEquals("plain:cipher-text", result.getData());
        assertEquals(1, callCount("decrypt"));
    }

    @Test
    void saveLogData_调用testService并返回成功() throws Exception {
        CommonResult result = controller.saveLogData("encrypted-json");

        assertSuccess(result);
        assertNull(result.getData());
        assertEquals(1, callCount("saveLogData"));
    }

    @Test
    void dailyTask_data为任务执行结果() {
        CommonResult result = controller.dailyTask();

        assertSuccess(result);
        assertEquals("任务执行成功", result.getData());
        assertEquals(1, callCount("dailyTask"));
    }

    @Test
    void config_data为配置字符串() {
        CommonResult result = controller.testConfig();

        assertSuccess(result);
        assertEquals("config-string", result.getData());
        assertEquals(1, callCount("testConfig"));
    }

    @Test
    void info_data为当前profile的客户信息() {
        CommonResult result = controller.getClientInfo();

        assertSuccess(result);
        assertEquals("客户A", result.getData());
    }

    @Test
    void otherInfo_data为其他行业客户信息() {
        CommonResult result = controller.getOtherClientInfo();

        assertSuccess(result);
        assertEquals("客户C", result.getData());
    }

    @Test
    void both_合并甲乙两个行业的待办列表() {
        CommonResult result = controller.getBoth();

        assertSuccess(result);
        assertEquals("todo-a1,todo-a2,todo-c1", result.getData());
    }

    @Test
    void service抛异常时Controller不吞异常直接上抛() {
        encryptThrows = true;

        assertThrows(RuntimeException.class, () -> controller.encrypt("hello"),
                "Service 异常应穿透 Controller，由全局异常处理器统一处理");
    }
}
