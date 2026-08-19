package com.my.work.util;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AesGcmUtils 单元测试：认证加密、随机 IV、错误密钥/篡改密文拒绝。
 */
class AesGcmUtilsTest {

    private static final String KEY_16 = "0123456789abcdef";          // 16 字节
    private static final String KEY_24 = "0123456789abcdef01234567";  // 24 字节
    private static final String KEY_32 = "0123456789abcdef0123456789abcdef"; // 32 字节
    private static final String PLAIN = "13012345678";

    @Test
    void roundTrip_with16ByteKey() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_16);
        assertEquals(PLAIN, AesGcmUtils.decrypt(enc, KEY_16));
    }

    @Test
    void roundTrip_with24ByteKey() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_24);
        assertEquals(PLAIN, AesGcmUtils.decrypt(enc, KEY_24));
    }

    @Test
    void roundTrip_with32ByteKey() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_32);
        assertEquals(PLAIN, AesGcmUtils.decrypt(enc, KEY_32));
    }

    @Test
    void ciphertext_isBase64OfIvPlusCipher() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_16);
        // 格式 Base64(12 字节随机 IV + 密文)，解码后长度必须 > 12
        byte[] decoded = Base64.getDecoder().decode(enc);
        assertTrue(decoded.length > 12);
    }

    @Test
    void randomIv_producesDifferentCiphertext() throws Exception {
        assertNotEquals(AesGcmUtils.encrypt(PLAIN, KEY_16), AesGcmUtils.encrypt(PLAIN, KEY_16));
    }

    @Test
    void wrongKey_isRejectedByGcmAuth() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_16);
        assertThrows(Exception.class, () -> AesGcmUtils.decrypt(enc, "0000000000000000"));
    }

    @Test
    void tamperedCiphertext_isRejectedByGcmAuth() throws Exception {
        String enc = AesGcmUtils.encrypt(PLAIN, KEY_16);
        byte[] bytes = Base64.getDecoder().decode(enc);
        bytes[bytes.length - 1] ^= 0x01; // 翻转密文最后一个字节
        String tampered = Base64.getEncoder().encodeToString(bytes);
        assertThrows(Exception.class, () -> AesGcmUtils.decrypt(tampered, KEY_16));
    }
}
