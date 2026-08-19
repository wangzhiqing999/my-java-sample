package com.my.work.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Md5Util 单元测试（标准参考值验证）。
 * 注：MD5 仅用于普通摘要/指纹场景，禁止用于密码哈希或签名（见检查清单 SEC-P1-2）。
 */
class Md5UtilTest {

    @Test
    void md5_knownValue_helloWorld() {
        // MD5("hello world")
        assertEquals("5eb63bbbe01eeed093cb22bb8f5acdc3", Md5Util.calculateMD5("hello world"));
    }
}
