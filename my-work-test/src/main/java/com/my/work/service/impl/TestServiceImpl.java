package com.my.work.service.impl;

import tools.jackson.databind.JsonNode;
import com.my.work.config.ConfigData;
import com.my.work.mapper.TestMapper;
import com.my.work.model.CommonResult;
import com.my.work.model.VersionResponse;
import com.my.work.sec.ECCCrypto;
import com.my.work.sec.ECCKeyReader;
import com.my.work.service.TestService;
import com.my.work.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static com.my.work.util.JsonUtil.OBJECT_MAPPER;

/**
 * 单纯的测试服务.
 * 给后续的业务， 提供一个参考的结构.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;


    private final ConfigData configData;


    /**
     * 测试方法：调用 Mapper 验证数据库连接与各存储过程调用链路.
     */
    @Override
    public void test() {
        int n = testMapper.selectTest();
        log.info("select test result: {}", n);

        String version = testMapper.selectFunction();
        log.info("select function result: {}", version);


        testMapper.callTestNopNor();
        testMapper.callTestHavepNor("Java 调用");


        Long id = testMapper.testHavepHaver("Java 调用");
        log.info("select test_havep_haver result: {}", id);



        Map<String, Object> jsonResult = testMapper.testHavepHaverj("Java 调用");
        log.info("select test_havep_haverj result: {}", jsonResult);
        for (Map.Entry<String, Object> entry : jsonResult.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();


            // 判空，避免空指针
            if (value == null) {
                log.info("Key: {}, Value: null", key);
                continue;
            }

            try {
                // 将 JSON 字符串解析为 JsonNode 对象
                JsonNode jsonNode = OBJECT_MAPPER.readTree(value.toString());

                // 提取指定字段的值
                int code = jsonNode.get("code").asInt(); // 数字类型用 asInt()
                String msg = jsonNode.get("msg").asText(); // 字符串类型用 asText()
                int id2 = jsonNode.get("id").asInt();

                // 输出解析后的字段值
                log.info("Key: {}", key);
                log.info("  code: {}", code);
                log.info("  msg: {}", msg);
                log.info("  id: {}", id2);
            } catch (Exception e) {
                // 解析失败时的异常处理
                log.error("解析 JSON 失败，原始值：{}", value, e);
            }
        }





        String jsonParam = "{\"log_text\":\"这是一条测试日志\"}";
        jsonResult = testMapper.testHavepjHaverj(jsonParam);
        log.info("select test_havepj_haverj result: {}", jsonResult);



        int[] testDatas = {1, 2, 3, 4};
        int result = testMapper.testSumArray(testDatas);
        log.info("testSumArray( 1,2,3,4 ) = {}", result);


    }


    /**
     * 获取服务器当前健康情况.
     *
     * @return 健康状态 Map（{@code status}=UP/DOWN，{@code message}=描述信息）
     */
    @Override
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        try {
            int n = testMapper.selectTest();
            result.put("status", "UP");
            result.put("message", "health");
        } catch (Exception e) {
            log.error("健康检查失败，数据库连接异常", e);
            result.put("status", "DOWN");
            result.put("message", "服务不可用，请稍后重试");
        }
        return result;
    }


    /**
     * 测试存储日志数据.
     *
     * <p>入参为 ECC 加密后的 JSON 字符串，先解密再调用存储过程入库。</p>
     *
     * @param requestData ECC 加密后的 JSON 字符串
     * @throws Exception 密钥解析、解密或存储过程调用失败时上抛
     */
    @Override
    public void saveLogData(String requestData) throws Exception {

        // 请求的字符串，是 json 字符串，加密后的.
        // 首先需要先做解密的操作.
        // 读取密钥
        PrivateKey privateKey = ECCKeyReader.readPrivateKeyFromString(configData.getPrivateKeyPem());

        // 解密
        String decryptedText = ECCCrypto.decrypt(requestData, privateKey);
        log.debug("解密后: {}", decryptedText);

        // 解密后的 json 字符串，作为参数，调用存储过程.
        Map<String, Object> jsonResult = testMapper.testHavepjHaverj(decryptedText);

        log.info("saveLogData result: {}", jsonResult);
    }



    /**
     * 测试的， 每天定时执行的任务.
     *
     * @return 任务执行结果描述
     */
    @Override
    public String dailyTask() {
        log.info("执行每日任务逻辑...");
        return "任务执行成功";
    }



    /**
     * 测试业务逻辑，在配置文件中定义的情况.
     *
     * @return 配置信息拼接结果字符串
     */
    @Override
    public String testConfig() {

        StringBuilder sb = new StringBuilder();

        // 配置文件里面，没有设置属性的，使用 类里面写的默认值.
        sb.append("testBooleanDefaultValue = ");
        sb.append(configData.isTestBooleanDefaultValue());
        sb.append("; testIntDefaultValue = ");
        sb.append(configData.getTestIntDefaultValue());
        sb.append("; testStringDefaultValue = ");
        sb.append(configData.getTestStringDefaultValue());

        // 配置文件里面，设置属性了，使用配置文件中的数值.
        sb.append(";\r\ntestBooleanDefaultValue2 = ");
        sb.append(configData.isTestBooleanDefaultValue2());
        sb.append("; testIntDefaultValue2 = ");
        sb.append(configData.getTestIntDefaultValue2());
        sb.append("; testStringDefaultValue2 = ");
        sb.append(configData.getTestStringDefaultValue2());

        return sb.toString();
    }



    /**
     * 测试保存配置信息.
     *
     * @param code 配置编码
     * @param data 配置数据（JSON 序列化后入库）
     */
    @Override
    public void testSaveConfig(String code, CommonResult data) {
        try {
            String json = JsonUtil.toJson(data);

            testMapper.fn_save_config(code, json);

        } catch (Exception ex){
            log.error("保存配置信息发生错误...", ex);
        }
    }


    /**
     * 测试获取配置信息.
     *
     * @param code 配置编码
     * @return 配置信息
     * @throws Exception 配置获取或解析失败时上抛，由全局异常处理器统一处理
     */
    @Override
    public CommonResult testLoadConfig(String code) throws Exception {
        String resultText = testMapper.fn_get_config(code);
        return OBJECT_MAPPER.readValue(resultText, CommonResult.class);
    }


    /**
     * 获取项目版本信息（读取 MANIFEST.MF 中的 Project-Version / Project-Name）.
     * 读取失败或字段缺失时回退默认值。
     *
     * @return 版本信息（version/projectName 均不为 null）
     */
    @Override
    public VersionResponse getVersion() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
            if (is == null) {
                return new VersionResponse("unknown", "test-service");
            }

            Manifest manifest = new Manifest(is);
            Attributes attributes = manifest.getMainAttributes();

            // 获取自定义的版本和项目名称
            String version = attributes.getValue("Project-Version");
            String name = attributes.getValue("Project-Name");

            // 兜底处理（防止读取失败）
            version = version != null ? version : "unknown";
            name = name != null ? name : "test-service";

            return new VersionResponse(version, name);
        } catch (IOException e) {
            // 异常时返回默认值
            log.warn("读取 MANIFEST.MF 失败，使用默认版本信息", e);
            return new VersionResponse("unknown", "test-service");
        }
    }


    /**
     * ECC 加密：读取配置的公钥后调用 {@link ECCCrypto#encrypt}.
     *
     * @param originalText 原始明文
     * @return Base64 密文
     * @throws Exception 公钥解析或加密失败时上抛
     */
    @Override
    public String encrypt(String originalText) throws Exception {
        // 读取密钥
        PublicKey publicKey = ECCKeyReader.readPublicKeyFromString(configData.getPublicKeyPem());

        log.debug("原始文本: {}", originalText);

        String encryptedText = ECCCrypto.encrypt(originalText, publicKey);
        log.debug("加密后: {}", encryptedText);

        return encryptedText;
    }


    /**
     * ECC 解密：读取配置的私钥后调用 {@link ECCCrypto#decrypt}.
     *
     * @param encryptedData Base64 密文
     * @return 解密后的明文
     * @throws Exception 私钥解析或解密失败时上抛
     */
    @Override
    public String decrypt(String encryptedData) throws Exception {
        // 读取密钥
        PrivateKey privateKey = ECCKeyReader.readPrivateKeyFromString(configData.getPrivateKeyPem());

        String decryptedText = ECCCrypto.decrypt(encryptedData, privateKey);
        log.debug("解密后: {}", decryptedText);

        return decryptedText;
    }


}
