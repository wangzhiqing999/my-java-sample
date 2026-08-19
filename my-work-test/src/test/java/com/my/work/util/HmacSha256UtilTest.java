package com.my.work.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * HmacSha256Util 单元测试（RFC 4231 标准参考值 + 格式校验）。
 */
class HmacSha256UtilTest {

    @Test
    void hmac_knownValue_rfc4231() {
        // RFC 4231 Test Case 2: key="Jefe", data="what do ya want for nothing?"
        assertEquals("5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843",
                HmacSha256Util.calculateHmacSHA256("what do ya want for nothing?", "Jefe"));
    }

    @Test
    void hmac_returns64HexChars() {
        String hmac = HmacSha256Util.calculateHmacSHA256("hello world", "123456");
        assertEquals(64, hmac.length());
        assertTrue(hmac.matches("[0-9a-fA-F]{64}"));
    }
}
