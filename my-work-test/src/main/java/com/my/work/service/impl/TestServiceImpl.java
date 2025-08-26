package com.my.work.service.impl;

import com.my.work.mapper.TestMapper;
import com.my.work.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
