package com.my.work.service.impl;

import com.my.work.config.ConfigData;
import com.my.work.mapper.TestMapper;
import com.my.work.sec.ECCCrypto;
import com.my.work.sec.ECCKeyReader;
import com.my.work.service.TestService;
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


}
