package com.my.work.util;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AesUtil（AES-CBC + 随机 IV）单元测试。
 */
class AesUtilTest {

    private static final String KEY = "0123456789abcdef"; // 16 字节

    @Test
    void roundTrip_with16ByteKey() throws Exception {
        String plain = "hello aes-cbc 中文测试";
        String enc = AesUtil.doEncrypt(plain, KEY);
        assertEquals(plain, AesUtil.doDecrypt(enc, KEY));
    }

    @Test
    void randomIv_producesDifferentCiphertext() throws Exception {
        assertNotEquals(AesUtil.doEncrypt("13012345678", KEY), AesUtil.doEncrypt("13012345678", KEY));
    }

    @Test
    void ciphertextStartsWith16ByteIv() throws Exception {
        String enc = AesUtil.doEncrypt("x", KEY);
        // 格式 Base64(16 字节随机 IV + 密文)，解码后长度必须 > 16
        byte[] decoded = Base64.getDecoder().decode(enc);
        assertTrue(decoded.length > 16);
    }
}
