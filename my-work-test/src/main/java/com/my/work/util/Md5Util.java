package com.my.work.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {


    /**
     * 计算字符串的 MD5 哈希值（32位十六进制字符串）
     * @param input 输入字符串
     * @return MD5 哈希值（32位十六进制）
     */
    public static String calculateMD5(String input) {
        try {
            // 1. 获取 MD5 算法的 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2. 将输入字符串转换为字节数组（指定 UTF-8 编码）
            byte[] inputBytes = input.getBytes("UTF-8");

            // 3. 计算 MD5 摘要（返回16字节的哈希值）
            byte[] md5Bytes = md.digest(inputBytes);

            // 4. 将字节数组转换为32位十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Bytes) {
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
            // 理论上不会抛出，因为 MD5 是标准算法
            throw new RuntimeException("MD5 algorithm not found", e);
        } catch (java.io.UnsupportedEncodingException e) {
            // UTF-8 是标准编码，理论上不会抛出
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }

    public static void main(String[] args) {
        String testInput = "hello world";
        String md5Result = calculateMD5(testInput);
        System.out.println("MD5 of \"" + testInput + "\": " + md5Result);
        // 输出：MD5 of "hello world": 5eb63bbbe01eeed093cb22bb8f5acdc3
    }

}
