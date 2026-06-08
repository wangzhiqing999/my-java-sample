package com.my.work.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HMAC-SHA256 哈希工具类
 * 计算字符串的 HMAC-SHA256 哈希值（64位十六进制字符串）
 */
public class HmacSha256Util {

    /**
     * 计算字符串的 HMAC-SHA256 哈希值（64位十六进制字符串）
     * @param input  输入字符串（待加密数据）
     * @param secret 密钥字符串
     * @return HMAC-SHA256 哈希值（64位十六进制小写）
     */
    public static String calculateHmacSHA256(String input, String secret) {
        try {
            // 1. 定义算法名称：HmacSHA256
            final String ALGORITHM = "HmacSHA256";

            // 2. 根据密钥生成 HMAC 密钥规格
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    ALGORITHM
            );

            // 3. 获取 HMAC-SHA256 实例并初始化
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKeySpec);

            // 4. 计算 HMAC-SHA256 摘要
            byte[] hmacBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));

            // 5. 将字节数组转换为 64 位十六进制字符串（与 MD5/SHA256 格式完全一致）
            StringBuilder hexString = new StringBuilder();
            for (byte b : hmacBytes) {
                // 处理负数，转为无符号整数
                int unsignedByte = b & 0xFF;
                // 转十六进制，不足两位补 0
                String hex = Integer.toHexString(unsignedByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HmacSHA256 algorithm not found", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid HMAC key", e);
        }
    }

    // 测试方法
    public static void main(String[] args) {
        String data = "hello world";
        String secretKey = "123456";
        String hmacSha256 = calculateHmacSHA256(data, secretKey);

        System.out.println("待加密数据：" + data);
        System.out.println("密钥：" + secretKey);
        System.out.println("HMAC-SHA256 结果：" + hmacSha256);
    }
}