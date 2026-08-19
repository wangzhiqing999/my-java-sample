package com.my.work.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Sha256Util 单元测试（标准参考值验证）。
 */
class Sha256UtilTest {

    @Test
    void sha256_knownValue_123456() {
        // SHA-256("123456")
        assertEquals("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92",
                Sha256Util.calculateSHA256("123456"));
    }

    @Test
    void sha256_knownValue_emptyString() {
        // SHA-256("")
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                Sha256Util.calculateSHA256(""));
    }
}
