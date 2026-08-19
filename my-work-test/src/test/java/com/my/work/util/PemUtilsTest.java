package com.my.work.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PemUtils 单元测试：PKCS8 PEM 私钥解析 + 非法算法名抛 IllegalArgumentException。
 */
class PemUtilsTest {

    @Test
    void readPrivateKey_fromPkcs8PemString() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        String b64 = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.UTF_8))
                .encodeToString(kp.getPrivate().getEncoded());
        String pem = "-----BEGIN PRIVATE KEY-----\n" + b64 + "\n-----END PRIVATE KEY-----";

        PrivateKey key = PemUtils.readPrivateKeyFromString(pem, "RSA");
        assertNotNull(key);
        assertEquals("RSA", key.getAlgorithm());
    }

    @Test
    void unknownAlgorithm_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                PemUtils.readPrivateKeyFromString(
                        "-----BEGIN PRIVATE KEY-----\nAAAA\n-----END PRIVATE KEY-----", "NOSUCH"));
    }
}
