package com.my.work.util;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RsaUtil 单元测试：RSA-2048 公钥加密 / 私钥解密 roundtrip 与错误密钥拒绝。
 */
class RsaUtilTest {

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }

    @Test
    void roundTrip_rsa2048() throws Exception {
        KeyPair kp = generateKeyPair();
        String pub = Base64.getEncoder().encodeToString(kp.getPublic().getEncoded());
        String priv = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());

        String plain = "hello rsa 中文加密测试";
        String enc = RsaUtil.encrypt(plain, pub);
        assertEquals(plain, RsaUtil.decrypt(enc, priv));
    }

    @Test
    void decryptWithWrongPrivateKey_fails() throws Exception {
        KeyPair kp1 = generateKeyPair();
        KeyPair kp2 = generateKeyPair();
        String pub1 = Base64.getEncoder().encodeToString(kp1.getPublic().getEncoded());
        String priv2 = Base64.getEncoder().encodeToString(kp2.getPrivate().getEncoded());

        String enc = RsaUtil.encrypt("secret", pub1);
        assertThrows(Exception.class, () -> RsaUtil.decrypt(enc, priv2));
    }
}
