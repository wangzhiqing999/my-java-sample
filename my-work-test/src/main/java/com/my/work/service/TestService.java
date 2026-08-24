package com.my.work.service;

import com.my.work.model.CommonResult;
import com.my.work.model.VersionResponse;

import java.util.Map;

/**
 * 单纯的测试服务.
 * 给后续的业务， 提供一个参考的结构.
 */
public interface TestService {


    /**
     * 测试方法：调用 Mapper 验证数据库连接与各存储过程调用链路.
     *
     * <p>FN-P0-1：数据库调用失败时抛出异常（MyBatis 运行时异常），
     * 由 {@code GlobalExceptionHandler} 统一兜底返回 500，不在此处吞异常。</p>
     *
     * @throws RuntimeException 任一 Mapper 调用失败时抛出（数据库连接异常/存储过程执行失败）
     */
    void test();


    /**
     * 获取服务器当前健康情况.
     *
     * @return 健康状态 Map（{@code status}=UP/DOWN，{@code message}=描述信息）
     */
    Map<String, Object> health();



    /**
     * 测试存储日志数据.
     *
     * <p>入参为 ECC 加密后的 JSON 字符串，先解密再调用存储过程入库。</p>
     *
     * @param requestData ECC 加密后的 JSON 字符串
     * @throws Exception 密钥解析、解密或存储过程调用失败时上抛
     */
    void saveLogData(String requestData) throws Exception;


    /**
     * 测试的， 每天定时执行的任务.
     *
     * @return 任务执行结果描述
     */
    String dailyTask();


    /**
     * 测试业务逻辑，在配置文件中定义的情况.
     *
     * @return 配置信息拼接结果字符串
     */
    String testConfig();



    /**
     * 测试保存配置信息.
     *
     * <p>FN-P0-1 修复：数据库写入失败时异常上抛（不再吞异常），
     * 由全局异常处理器统一返回 500，避免调用方误判保存成功。</p>
     *
     * @param code 配置编码
     * @param data 配置数据（JSON 序列化后入库）
     * @throws Exception JSON 序列化或存储过程调用失败时上抛
     */
    void testSaveConfig(String code, CommonResult data) throws Exception;


    /**
     * 测试获取配置信息.
     *
     * @param code 配置编码
     * @return 配置信息
     * @throws Exception 配置获取或解析失败时上抛
     */
    CommonResult testLoadConfig(String code) throws Exception;


    /**
     * 获取项目版本信息（读取 MANIFEST.MF）.
     *
     * @return 版本信息（含 projectName），读取失败时回退默认值
     */
    VersionResponse getVersion();


    /**
     * ECC 加密：读取配置的公钥后调用 {@link com.my.work.sec.ECCCrypto#encrypt}.
     *
     * @param originalText 原始明文
     * @return Base64 密文
     * @throws Exception 公钥解析或加密失败时上抛
     */
    String encrypt(String originalText) throws Exception;


    /**
     * ECC 解密：读取配置的私钥后调用 {@link com.my.work.sec.ECCCrypto#decrypt}.
     *
     * @param encryptedData Base64 密文
     * @return 解密后的明文
     * @throws Exception 私钥解析或解密失败时上抛
     */
    String decrypt(String encryptedData) throws Exception;

}
