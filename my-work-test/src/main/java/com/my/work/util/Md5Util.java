package com.my.work.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 摘要工具类（仅用于非安全场景）
 *
 * <p><b>安全警示（SEC-P1-2）</b>：MD5 已存在已知碰撞攻击，<b>禁止</b>用于以下场景：</p>
 * <ul>
 *   <li>密码哈希（应使用 BCrypt/Argon2/PBKDF2）</li>
 *   <li>消息签名 / HMAC / 数字证书指纹</li>
 *   <li>任何安全敏感、可被攻击者构造碰撞数据的场景</li>
 * </ul>
 *
 * <p>仅允许用于非安全校验：数据指纹、去重、与旧系统（如数据库 MD5() 函数）兼容等。</p>
 */
public final class Md5Util {

    private Md5Util() {
    }

    /**
     * 计算字符串的 MD5 摘要值（32位十六进制字符串）
     *
     * <p><b>注意</b>：MD5 存在碰撞风险，仅限非安全用途；禁止用于密码哈希或签名。</p>
     *
     * @param input 输入字符串
     * @return MD5 摘要值（32位十六进制）
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

}
