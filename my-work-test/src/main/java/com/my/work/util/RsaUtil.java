package com.my.work.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * RSA 加密解密工具
 * 密钥：2048位
 * 加密：公钥
 * 解密：私钥
 */
public final class RsaUtil {

    private RsaUtil() {
    }

    // 加密算法
    private static final String RSA_ALGORITHM = "RSA";
    // 加密填充模式
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 公钥加密
     * @param data 明文
     * @param publicKeyStr Base64公钥字符串
     * @return Base64加密密文
     * @throws Exception 密钥解析或加密失败时抛出
     */
    public static String encrypt(String data, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptBytes);
    }

    /**
     * 私钥解密
     * @param encryptData Base64密文
     * @param privateKeyStr Base64私钥字符串
     * @return 明文
     * @throws Exception 密钥解析或解密失败时抛出
     */
    public static String decrypt(String encryptData, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodeBytes = Base64.getDecoder().decode(encryptData);
        byte[] dataBytes = cipher.doFinal(decodeBytes);
        return new String(dataBytes, StandardCharsets.UTF_8);
    }

    /**
     * 解析公钥字符串为公钥对象
     */
    private static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(spec);
    }

    /**
     * 解析私钥字符串为私钥对象
     */
    private static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(spec);
    }
}