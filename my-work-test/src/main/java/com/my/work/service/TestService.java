package com.my.work.service;

import com.my.work.model.CommonResult;

/**
 * 单纯的测试服务.
 * 给后续的业务， 提供一个参考的结构.
 */
public interface TestService {


    void test();


    /**
     * 测试存储日志数据.
     *
     * @param requestData
     */
    void saveLogData(String requestData) throws Exception;


    /**
     * 测试的， 每天定时执行的任务.
     * @return
     */
    String dailyTask();


    /**
     * 测试业务逻辑，在配置文件中定义的情况.
     * @return
     */
    String testConfig();



    /**
     * 测试保存配置信息.
     * @param code
     * @param data
     */
    void testSaveConfig(String code, CommonResult data);


    /**
     * 测试获取配置信息.
     * @param code
     * @return
     */
    CommonResult testLoadConfig(String code);

}
