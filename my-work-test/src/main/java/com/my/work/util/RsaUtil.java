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
public class RsaUtil {

    // 加密算法
    private static final String RSA_ALGORITHM = "RSA";
    // 加密填充模式
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 公钥加密
     * @param data 明文
     * @param publicKeyStr Base64公钥字符串
     * @return Base64加密密文
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

    // 测试主方法
    public static void main(String[] args) throws Exception {

        // 这里是单纯的测试。
        // 实际使用的时候，使用下面的命令，来生成 私钥、公钥.
        // openssl genrsa -out rsa_priv_key.pem 2048
        // openssl rsa -in rsa_priv_key.pem -pubout -out rsa_pub_key.pem

        // 生成私钥公钥文件之后， 使用下面的命令，移除换行.
        // sed '/^-/d' rsa_pub_key.pem | tr -d '\n'
        // sed '/^-/d' rsa_priv_key.pem | tr -d '\n'

        // 然后，复制最终的结果，到下面来进行测试。
        String publicKey = "这里是公钥的内容，只有一行内容。";
        String privateKey = "这里是私钥的内容，只有一行内容。";


        String content = "hello rsa 加密测试";
        // 加密
        String cipherText = encrypt(content, publicKey);
        System.out.println("加密结果：" + cipherText);
        // 解密
        String plainText = decrypt(cipherText, privateKey);
        System.out.println("解密结果：" + plainText);
    }
}