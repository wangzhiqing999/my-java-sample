package com.my.work.sec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

/**
 * ECC PEM 密钥读取工具类.
 *
 * <p>基于 BouncyCastle 的 {@link PEMParser} 读取 PEM 格式的 EC 私钥/公钥，
 * 支持从文件或字符串解析，私钥格式支持 PKCS#8 与 PEMKeyPair。</p>
 *
 * <p>BouncyCastle Provider 由 {@link com.my.work.config.SecurityConfig} 通过
 * Spring {@code @Bean} 统一注册；非 Spring 环境由 {@link #ensureProvider()} 惰性注册兜底。</p>
 */
public class ECCKeyReader {

    /**
     * 确保 BouncyCastle Provider 已注册（非 Spring 环境的惰性兜底）.
     *
     * <p>Spring 环境下由 {@link com.my.work.config.SecurityConfig} 注册，
     * 此方法为 no-op；非 Spring 环境（如单元测试）首次调用时注册。</p>
     */
    private static void ensureProvider() {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 从 PEM 文件读取私钥.
     *
     * @param filename PEM 文件路径
     * @return 解析后的私钥对象
     * @throws Exception 文件读取或 PEM 解析失败时抛出
     */
    public static PrivateKey readPrivateKeyFromFile(String filename) throws Exception {
        ensureProvider();
        try (PEMParser pemParser = new PEMParser(new FileReader(filename))) {
            return readPrivateKey(pemParser);
        }
    }

    /**
     * 从 PEM 字符串读取私钥.
     *
     * @param privateKeyPem PEM 格式的私钥字符串
     * @return 解析后的私钥对象
     * @throws Exception PEM 解析失败时抛出
     */
    public static PrivateKey readPrivateKeyFromString(String privateKeyPem) throws Exception {
        ensureProvider();
        try (PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem))) {
            return readPrivateKey(pemParser);
        }
    }

    private static PrivateKey readPrivateKey(PEMParser pemParser) throws IOException {
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        if (object instanceof PEMKeyPair) {
            PEMKeyPair keyPair = (PEMKeyPair) object;
            return converter.getPrivateKey(keyPair.getPrivateKeyInfo());
        } else if (object instanceof org.bouncycastle.asn1.pkcs.PrivateKeyInfo) {
            return converter.getPrivateKey((org.bouncycastle.asn1.pkcs.PrivateKeyInfo) object);
        } else {
            throw new PEMException("Unsupported private key format: " + object.getClass().getName());
        }
    }

    /**
     * 从 PEM 文件读取公钥.
     *
     * @param filename PEM 文件路径
     * @return 解析后的公钥对象
     * @throws Exception 文件读取或 PEM 解析失败时抛出
     */
    public static PublicKey readPublicKeyFromFile(String filename) throws Exception {
        ensureProvider();
        try (PEMParser pemParser = new PEMParser(new FileReader(filename))) {
            return readPublicKey(pemParser);
        }
    }

    /**
     * 从 PEM 字符串读取公钥.
     *
     * @param publicKeyPem PEM 格式的公钥字符串
     * @return 解析后的公钥对象
     * @throws Exception PEM 解析失败时抛出
     */
    public static PublicKey readPublicKeyFromString(String publicKeyPem) throws Exception {
        ensureProvider();
        try (PEMParser pemParser = new PEMParser(new StringReader(publicKeyPem))) {
            return readPublicKey(pemParser);
        }
    }

    private static PublicKey readPublicKey(PEMParser pemParser) throws IOException {
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        if (object instanceof org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) {
            return converter.getPublicKey((org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) object);
        } else if (object instanceof PEMKeyPair) {
            PEMKeyPair keyPair = (PEMKeyPair) object;
            return converter.getPublicKey(keyPair.getPublicKeyInfo());
        } else {
            throw new PEMException("Unsupported public key format: " + object.getClass().getName());
        }
    }

    /**
     * 从 PEM 字符串同时读取密钥对.
     *
     * @param privateKeyPem 包含公钥与私钥的 PEM 字符串（PEMKeyPair 格式）
     * @return 密钥对（公钥 + 私钥）
     * @throws Exception PEM 解析失败时抛出
     */
    public static KeyPair readKeyPairFromString(String privateKeyPem) throws Exception {
        ensureProvider();
        try (PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair) {
                PEMKeyPair keyPair = (PEMKeyPair) object;
                PrivateKey privateKey = converter.getPrivateKey(keyPair.getPrivateKeyInfo());
                PublicKey publicKey = converter.getPublicKey(keyPair.getPublicKeyInfo());
                return new KeyPair(publicKey, privateKey);
            } else {
                throw new PEMException("Unsupported key pair format: " + object.getClass().getName());
            }
        }
    }
}