package com.my.work.service;

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

}
