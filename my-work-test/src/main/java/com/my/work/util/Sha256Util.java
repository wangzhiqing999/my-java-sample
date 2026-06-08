package com.my.work.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 哈希工具类
 * 计算字符串的 SHA256 哈希值（64位十六进制字符串）
 */
public class Sha256Util {

    /**
     * 计算字符串的 SHA256 哈希值（64位十六进制字符串）
     * @param input 输入字符串
     * @return SHA256 哈希值（64位十六进制）
     */
    public static String calculateSHA256(String input) {
        try {
            // 1. 获取 SHA-256 算法的 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 2. 将输入字符串转换为字节数组（指定 UTF-8 编码）
            byte[] inputBytes = input.getBytes("UTF-8");

            // 3. 计算 SHA256 摘要（返回32字节的哈希值）
            byte[] sha256Bytes = md.digest(inputBytes);

            // 4. 将字节数组转换为64位十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : sha256Bytes) {
                // 将字节转换为无符号整数（&0xff 处理负数）
                int unsignedByte = b & 0xFF;
                // 转换为十六进制，若不足两位则补0
                String hex = Integer.toHexString(unsignedByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // 理论上不会抛出，因为 SHA-256 是标准算法
            throw new RuntimeException("SHA-256 algorithm not found", e);
        } catch (java.io.UnsupportedEncodingException e) {
            // UTF-8 是标准编码，理论上不会抛出
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }

    // 测试方法
    public static void main(String[] args) {
        String testStr = "123456";
        String sha256 = calculateSHA256(testStr);
        System.out.println("原文：" + testStr);
        System.out.println("SHA256哈希值：" + sha256);
    }
}