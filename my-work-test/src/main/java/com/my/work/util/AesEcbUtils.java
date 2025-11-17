package com.my.work.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class AesEcbUtils {

    // 加密算法：AES，模式：ECB，填充方式：PKCS5Padding
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    // 默认的密钥，与PostgreSQL存储过程中使用的保持一致
    private static final String DEFAULT_AES_KEY = "it_is_a_test_pwd";


    /**
     * 加密手机号
     * @param mobile 待加密的手机号
     * @param aesKey AES的密钥.
     * @return 加密后的Base64字符串
     * @throws Exception 加密过程中可能出现的异常
     */
    public static String encryptMobile(String mobile, String aesKey) throws Exception {
        // 创建密钥规格，使用AES算法
        SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");

        // 获取加密器实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化加密模式
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // 执行加密并进行Base64编码
        byte[] encrypted = cipher.doFinal(mobile.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 解密手机号
     * @param encryptedMobile 加密后的Base64字符串
     * @param aesKey AES的密钥.
     * @return 解密后的手机号
     * @throws Exception 解密过程中可能出现的异常
     */
    public static String decryptMobile(String encryptedMobile, String aesKey) throws Exception {
        // 创建密钥规格，使用AES算法
        SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");

        // 获取加密器实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化解密模式
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 先进行Base64解码，再执行解密
        byte[] decoded = Base64.getDecoder().decode(encryptedMobile);
        byte[] decrypted = cipher.doFinal(decoded);

        return new String(decrypted, StandardCharsets.UTF_8);
    }


    /**
     * 加密手机号
     * @param mobile 待加密的手机号
     * @return 加密后的Base64字符串
     * @throws Exception 加密过程中可能出现的异常
     */
    public static String encryptMobile(String mobile) throws Exception {
        return encryptMobile(mobile, DEFAULT_AES_KEY);
    }

    /**
     * 解密手机号
     * @param encryptedMobile 加密后的Base64字符串
     * @return 解密后的手机号
     * @throws Exception 解密过程中可能出现的异常
     */
    public static String decryptMobile(String encryptedMobile) throws Exception {
        return decryptMobile(encryptedMobile, DEFAULT_AES_KEY);
    }



    // 测试方法
    public static void main(String[] args) {
        try {
            String originalMobile = "13012345678";
            System.out.println("原始手机号: " + originalMobile);

            // 加密
            String encrypted = encryptMobile(originalMobile);
            System.out.println("加密后: " + encrypted);

            // 解密
            String decrypted = decryptMobile(encrypted);
            System.out.println("解密后: " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



/*

PostgreSQL存储过程：
数据库需要安装 pgcrypto 扩展， 默认是安装在 public 模式下。
如果自己指定了， 扩展安装在特定的模式下，那么，下面的 encrypt 与 decrypt 需要增加具体模式的名称.

CREATE OR REPLACE FUNCTION func_encrypt_mobile(
    p_mobile character varying,
	p_pwd character varying DEFAULT 'it_is_a_test_pwd'::character varying
)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE
	v_result	VARCHAR;
BEGIN

	v_result := encode(
		encrypt(
			convert_to(p_mobile, 'UTF8'),
			convert_to(p_pwd, 'UTF8'),
			'aes-ecb/pad:pkcs'), 'base64');

	RETURN v_result;

END;
$function$
;


CREATE OR REPLACE FUNCTION func_decrypt_mobile(
	p_mobile character varying,
	p_pwd character varying DEFAULT 'it_is_a_test_pwd'::character varying
)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE
	v_result	VARCHAR;
BEGIN

	v_result := convert_from(
		decrypt(
		  decode(p_mobile, 'base64'),
		  convert_to(p_pwd, 'UTF8'),
		  'aes-ecb/pad:pkcs'), 'UTF8');

	RETURN v_result;

END;
$function$
;


select func_encrypt_mobile('13012345678'), func_decrypt_mobile('2/yzM21rDynRNzxo7rAVPA==')

也就是可以实现 Java 加密、 PostgreSQL存储过程解密。
或者是 PostgreSQL存储过程加密、Java解密的操作。

 */
}
