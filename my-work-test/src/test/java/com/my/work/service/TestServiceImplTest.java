package com.my.work.service;

import com.my.work.config.ConfigData;
import com.my.work.mapper.TestMapper;
import com.my.work.model.VersionResponse;
import com.my.work.service.impl.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestServiceImpl 单元测试（M-P1-1 下沉后新增）.
 *
 * <p>用 JDK 动态代理桩替代 MyBatis 接口（本用例不触发 Mapper 调用），
 * 密钥取自 application.yml 的演示密钥，验证 ECC 加解密 roundtrip 与版本信息兜底。
 */
class TestServiceImplTest {

    /** 演示私钥（与 application.yml my.work.config.privateKeyPem 一致）. */
    private static final String PRIVATE_KEY_PEM = "-----BEGIN EC PRIVATE KEY-----\n"
            + "MHQCAQEEIFUmcJTzQ59/1Am2RS1wdYmPvyVTW9vTBIfXkBYRE/VSoAcGBSuBBAAK\n"
            + "oUQDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazEKPiJ09yf6cVlnf73YgYlWzV0\n"
            + "4RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==\n"
            + "-----END EC PRIVATE KEY-----\n";

    /** 演示公钥（与 application.yml my.work.config.publicKeyPem 一致）. */
    private static final String PUBLIC_KEY_PEM = "-----BEGIN PUBLIC KEY-----\n"
            + "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazE\n"
            + "KPiJ09yf6cVlnf73YgYlWzV04RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==\n"
            + "-----END PUBLIC KEY-----\n";

    private TestServiceImpl service;

    @BeforeEach
    void setUp() {
        // MyBatis Mapper 桩：本用例不触发其方法，返回 null 即可
        TestMapper mapperStub = (TestMapper) Proxy.newProxyInstance(
                TestMapper.class.getClassLoader(),
                new Class<?>[]{TestMapper.class},
                (proxy, method, args) -> null);

        ConfigData configData = new ConfigData();
        configData.setPrivateKeyPem(PRIVATE_KEY_PEM);
        configData.setPublicKeyPem(PUBLIC_KEY_PEM);

        service = new TestServiceImpl(mapperStub, configData);
    }

    @Test
    void getVersion_返回非空且字段不为null() {
        VersionResponse version = service.getVersion();

        assertNotNull(version);
        assertNotNull(version.version());
        assertNotNull(version.projectName());
    }

    @Test
    void encrypt_返回Base64密文() throws Exception {
        String cipherText = service.encrypt("hello-sec-p1-3");

        assertNotNull(cipherText);
        // Base64 可解码
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        assertTrue(decoded.length > 0);
    }

    @Test
    void encrypt_decrypt_往返还原原文() throws Exception {
        String plainText = "M-P1-1 业务下沉回归: ECDH+AES-GCM roundtrip";

        String cipherText = service.encrypt(plainText);
        String decryptedText = service.decrypt(cipherText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    void decrypt_篡改密文被认证拒绝() {
        // GCM 认证标签校验，错误密文应抛异常而非静默返回
        assertThrows(Exception.class, () -> service.decrypt("bm90LWEtdmFsaWQtY2lwaGVyLXRleHQ="));
    }
}
