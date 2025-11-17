package com.my.work.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class DesEncryptor {

    /**
     * 对明文进行DES-CBC加密，匹配pgcrypto的加密结果
     * @param plaintext 明文（如"13012345678"）
     * @param key 密钥（如"it_is_a_test_password"）
     * @return 加密后的十六进制字符串（小写）
     * @throws Exception 加密过程中可能抛出的异常
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        // 1. 处理密钥：DES密钥长度固定为8字节，取密钥UTF-8编码的前8字节
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] desKey = new byte[8];
        System.arraycopy(keyBytes, 0, desKey, 0, Math.min(keyBytes.length, 8));

        // 2. 明文转换为UTF-8字节数组（与PostgreSQL的bytea转换一致）
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        // 3. 初始化向量(IV)：pgcrypto在CBC模式下默认使用全零IV（8字节）
        byte[] iv = new byte[8]; // 全零IV

        // 4. 初始化DES加密器（CBC模式 + PKCS5填充，与pgcrypto的'des-cbc/pad:pkcs'匹配）
        SecretKeySpec secretKey = new SecretKeySpec(desKey, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // 5. 执行加密并转为十六进制字符串（小写）
        byte[] encryptedBytes = cipher.doFinal(plaintextBytes);
        return HexFormat.of().withUpperCase().formatHex(encryptedBytes);
    }



    public static String encrypt(String plaintext) throws Exception {
        return encrypt(plaintext,"it_is_a_test_password");
    }



    // 测试方法
    public static void main(String[] args) throws Exception {
        String plaintext = "13012345678";
        String result = encrypt(plaintext);
        System.out.println(result);
    }



/*

参考的 Postgresql 查询.

加密：
SELECT
  encode(
    encrypt(
      '13012345678'::bytea,
      'it_is_a_test_password'::bytea,
      'des-cbc/pad:pkcs'), 'hex');
-----
6c2c220e55f38037d7c2123f3497b5ce



解密:
select
  encode(
    decrypt(
        decode('6c2c220e55f38037d7c2123f3497b5ce', 'hex'),
        'it_is_a_test_password'::bytea,
        'des-cbc/pad:pkcs'
    ), 'escape')
-----
13012345678

 */


}
