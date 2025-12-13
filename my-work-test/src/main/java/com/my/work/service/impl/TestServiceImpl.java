package com.my.work.service.impl;

import com.my.work.config.ConfigData;
import com.my.work.mapper.TestMapper;
import com.my.work.model.CommonResult;
import com.my.work.sec.ECCCrypto;
import com.my.work.sec.ECCKeyReader;
import com.my.work.service.TestService;
import com.my.work.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Map;

/**
 * 单纯的测试服务.
 * 给后续的业务， 提供一个参考的结构.
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;


    @Autowired
    private ConfigData configData;


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


        String jsonParam = "{\"log_text\":\"这是一条测试日志\"}";
        jsonResult = testMapper.testHavepjHaverj(jsonParam);
        log.info("select test_havepj_haverj result: {}", jsonResult);
    }



    /**
     * 测试存储日志数据.
     *
     * @param requestData
     */
    public void saveLogData(String requestData) throws Exception {

        // 请求的字符串，是 json 字符串，加密后的.
        // 首先需要先做解密的操作.
        // 读取密钥
        PrivateKey privateKey = ECCKeyReader.readPrivateKeyFromString(configData.getPrivateKeyPem());

        // 解密
        String decryptedText = ECCCrypto.decrypt(requestData, privateKey);
        log.debug("解密后: " + decryptedText);

        // 解密后的 json 字符串，作为参数，调用存储过程.
        Map<String, Object> jsonResult = testMapper.testHavepjHaverj(decryptedText);

        log.info("saveLogData result: {}", jsonResult);
    }



    /**
     * 测试的， 每天定时执行的任务.
     * @return
     */
    public String dailyTask() {
        log.info("执行每日任务逻辑...");
        return "任务执行成功";
    }



    /**
     * 测试业务逻辑，在配置文件中定义的情况.
     * @return
     */
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
     * @param code
     * @return
     */
    public CommonResult testLoadConfig(String code) {

        try {
            String resultText = testMapper.fn_get_config(code);
            CommonResult result = JsonUtil.objectMapper.readValue(resultText, CommonResult.class);
            return result;
        } catch (Exception ex){
            log.error("保存配置信息发生错误...", ex);
            return null;
        }

    }


}
