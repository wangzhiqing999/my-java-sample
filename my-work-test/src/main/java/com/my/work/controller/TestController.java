package com.my.work.controller;

import com.my.work.model.CommonResult;
import com.my.work.model.SaveConfigRequest;
import com.my.work.service.ClientService;
import com.my.work.service.OtherClientService;
import com.my.work.service.TestService;
import com.my.work.util.JsonUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的、测试用的控制器.
 *
 * <p>所有接口统一返回 {@link CommonResult}（M-P1-2 修复）：
 * <ul>
 *   <li>成功：code=200，msg="success"，data=业务数据（对象/字符串 JSON 序列化后放入）</li>
 *   <li>业务无返回值：data=null</li>
 *   <li>异常：由 {@code GlobalExceptionHandler} 统一兜底，返回 code=400 或 500</li>
 * </ul>
 */
@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
@Validated
public class TestController {

    private final TestService testService;

    /**
     * 注意：这个服务，是有多个实现的，具体使用哪一个实现，配置在 application.yml 文件中的 spring-->profiles-->active 下面.
     */
    private final ClientService clientService;

    /**
     * 注意：这个服务，是有多个实现的，具体使用哪一个实现，配置在 application.yml 文件中的 spring-->profiles-->active 下面.
     */
    private final OtherClientService otherClientService;

    /** 成功响应码. */
    private static final int CODE_SUCCESS = 200;
    /** 成功消息. */
    private static final String MSG_SUCCESS = "success";

    /**
     * 构造成功响应（无数据）.
     */
    private CommonResult success() {
        return new CommonResult(CODE_SUCCESS, MSG_SUCCESS);
    }

    /**
     * 构造成功响应（字符串数据，直接放入 data）.
     */
    private CommonResult success(String data) {
        return new CommonResult(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    /**
     * 构造成功响应（对象数据，序列化为 JSON 字符串后放入 data）.
     */
    private CommonResult success(Object data) {
        return new CommonResult(CODE_SUCCESS, MSG_SUCCESS, JsonUtil.toJson(data));
    }

    /**
     * 单纯的测试.
     * @return 统一响应体
     */
    @GetMapping("/get")
    public CommonResult get() {
        testService.test();
        return success();
    }

    /**
     * 获取项目版本信息
     * @return 包含版本号的响应体
     */
    @GetMapping("/version")
    public CommonResult getVersion() {
        return success(testService.getVersion());
    }

    /**
     * 健康监测.
     * @return 统一响应体，data 为健康状态信息（JSON 序列化）
     */
    @GetMapping("/health")
    public CommonResult health() {
        return success(testService.health());
    }

    /**
     * 测试保存配置信息.
     * http://localhost:8080/test/save-config?code=1&msg=test_message
     * @param request 配置参数（{@code code} 必填且≥0、{@code msg} 非空白，Bean Validation 校验，SEC-P1-3）
     * @return 统一响应体
     */
    @PostMapping("/save-config")
    public CommonResult saveConfig(@Valid @ModelAttribute SaveConfigRequest request) {
        CommonResult testData = new CommonResult();
        testData.setCode(request.getCode());
        testData.setMsg(request.getMsg());
        testService.testSaveConfig("TEST", testData);
        return success();
    }

    /**
     * 测试保存配置信息.
     * http://localhost:8080/test/read-config
     * @return 统一响应体，data 为配置信息（JSON 序列化）
     * @throws Exception 配置读取或 JSON 解析失败时上抛，由全局异常处理器统一处理
     */
    @GetMapping("/read-config")
    public CommonResult readConfig() throws Exception {
        return success(testService.testLoadConfig("TEST"));
    }

    /**
     * 测试一个加密的处理.
     *
     * 一般情况下，不使用，这里时单纯为了测试解密的处理，才提供加密的接口。
     * 加密出来结果了， 然后去测试解密的接口.
     *
     * @param originalText 原始文本
     * @return 统一响应体，data 为加密后文本
     * @throws Exception 密钥解析或加密失败时上抛，由全局异常处理器统一处理
     */
    @PostMapping("/encrypt")
    public CommonResult encrypt(@RequestBody @NotBlank(message = "原始文本不能为空") String originalText) throws Exception {
        return success(testService.encrypt(originalText));
    }

    /**
     * 测试一个解密的处理.
     *
     * 业务逻辑
     * 客户端，使用公钥， 加密一个数据， 然后， Base64编码， 将整个 字符串， POST 到服务器接口上.
     * 服务端，接收到客户端提交过来的数据后， 使用 私钥，解密数据。
     *
     * @param encryptedData 加密数据
     * @return 统一响应体，data 为解密后文本
     * @throws Exception 密钥解析或解密失败时上抛，由全局异常处理器统一处理
     */
    @PostMapping("/decrypt")
    public CommonResult decrypt(@RequestBody @NotBlank(message = "加密数据不能为空") String encryptedData) throws Exception {
        return success(testService.decrypt(encryptedData));
    }

    /**
     * 测试一个 先解密， 后调用存储过程的处理.
     * @param encryptedData 加密数据
     * @return 统一响应体
     * @throws Exception 密钥解析、解密或存储过程调用失败时上抛，由全局异常处理器统一处理
     */
    @PostMapping("/save-log")
    public CommonResult saveLogData(@RequestBody @NotBlank(message = "加密数据不能为空") String encryptedData) throws Exception {
        log.debug("/save-log start!");
        testService.saveLogData(encryptedData);
        return success();
    }

    /**
     * 测试被定时调用的接口
     * @return 统一响应体，data 为任务执行结果
     */
    @GetMapping("/daily-task")
    public CommonResult dailyTask() {
        // 调用服务，完成 需要定时执行的任务.
        return success(testService.dailyTask());
    }

    /**
     * 测试默认的配置.
     * 也就是：配置的类里面，定义了属性的初始数值， 但是在配置文件中， 没有定义属性的值。
     * 系统在默认情况下，能正常运行，要具体做调整的时候，再修改配置文件， 针对特定的属性，进行修改配置.
     * @return 统一响应体，data 为配置信息字符串
     */
    @GetMapping("/config")
    public CommonResult testConfig() {
        log.debug("/config start!");
        return success(testService.testConfig());
    }

    /**
     * 测试的，相同接口，不同实现的处理.
     * 如果更换其它实现的情况下，需要修改 application.yml 配置文件.
     * 配置在 application.yml 文件中的 spring-->profiles-->active 下面.
     *
     * 测试的机制，
     * 先启动项目，然后访问 http://localhost:8080/test/info
     * 得到的是 客户A 的实现。
     *
     * 停止项目，application.yml 文件中的 spring-->profiles-->active 修改为 clientB
     * 再运行项目， 刷新 http://localhost:8080/test/info
     * 得到的是 客户B 的实现。
     *
     * 也就是一套代码， 发布给不同的客户使用，  不同的客户， 又有其自己特有的 业务逻辑， 通过不同的实现，以及配置文件， 来完成， 发布到不同的客户那里，实现特定客户的功能。
     *
     * @return 统一响应体，data 为客户信息
     */
    @GetMapping("/info")
    public CommonResult getClientInfo() {
        log.debug("/info start!");
        return success(clientService.getClientInfo());
    }

    /**
     * 获取其他行业（乙行业）客户信息.
     * 实现类由 application.yml 中 spring-->profiles-->active 决定（otherClientC / otherClientD / otherClientEmpty）.
     *
     * @return 统一响应体，data 为客户信息
     */
    @GetMapping("/other-info")
    public CommonResult getOtherClientInfo() {
        log.debug("/other-info start!");
        return success(otherClientService.getClientInfo());
    }

    /**
     * 假设我这套系统， 一开始， 是为 甲行业做的。
     * 定义了  甲行业的接口： ClientService
     * 为 甲行业的两家公司， 分别写了实现： ClientAServiceImpl， ClientBServiceImpl
     *
     * 现在，业务拓展了， 准备为  相似的 乙行业写。
     * 定义了  乙行业的接口： OtherClientService
     * 为 乙行业的两家公司， 分别写了实现： OtherClientCServiceImpl， OtherClientDServiceImpl
     *
     * 处理的时候， 可能是需要使用 甲行业的 部分代码， 又要使用 乙行业的部分代码.
     *
     * 测试的机制，
     * 先启动项目，然后访问 http://localhost:8080/test/both
     * 得到的是 甲行业 客户A 的实现 + 乙行业 客户C 的实现。
     *
     * 停止项目，application.yml 文件中的 spring-->profiles-->active 修改为 clientB,otherClientEmpty
     * 得到的是 甲行业 客户B 的实现 +  乙行业 的空白实现。
     *
     * @return 统一响应体，data 为两个行业客户实现的合并列表（逗号分隔字符串）
     */
    @GetMapping("/both")
    public CommonResult getBoth() {
        log.debug("/both start!");

        List<String> resultList = clientService.getTodoList();
        List<String> otherResultList = otherClientService.getTodoList("test");

        List<String> todoList = new ArrayList<>();
        todoList.addAll(resultList);
        todoList.addAll(otherResultList);

        return success(String.join(",", todoList));
    }
}
