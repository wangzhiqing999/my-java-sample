package com.my.work.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * AES-CBC 加解密工具。
 *
 * <p>安全设计（P0-2 修复）：
 * <ul>
 *   <li>每次加密生成 16 字节随机 IV，杜绝固定 IV 重用（CBC 模式下固定 IV 会泄露明文前缀模式）</li>
 *   <li>IV 与密文拼接后整体 Base64 编码：返回格式 {@code Base64(IV + ciphertext)}，解密时自动提取 IV</li>
 *   <li>密钥由调用方传入，支持 128/192/256 位（16/24/32 字节），长度非法直接拒绝</li>
 *   <li>注意：旧版硬编码 IV 生成的密文（无 IV 前缀）无法用本实现解密，需用新密钥重新加密</li>
 * </ul>
 */
public final class AesUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";
    private static final int IV_LENGTH = 16;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private AesUtil() {
    }

    /**
     * AES-CBC 加密，返回 {@code Base64(IV + ciphertext)}。
     *
     * @param plainText 明文，非空
     * @param key       密钥，UTF-8 字节长度必须为 16/24/32
     * @return Base64 编码的 IV+密文
     * @throws Exception 参数非法、密钥长度不合法或加密失败时抛出
     */
    public static String doEncrypt(String plainText, String key) throws Exception {
        if (plainText == null) {
            throw new IllegalArgumentException("plainText must not be null");
        }
        byte[] keyBytes = validateKey(key);

        byte[] iv = new byte[IV_LENGTH];
        SECURE_RANDOM.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, AES), new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 解密 {@code Base64(IV + ciphertext)} 格式的密文。
     *
     * @param encryptedText 密文，非空
     * @param key           密钥，UTF-8 字节长度必须为 16/24/32
     * @return 解密后的明文字符串
     * @throws Exception 参数非法、密钥长度不合法或解密失败时抛出
     */
    public static String doDecrypt(String encryptedText, String key) throws Exception {
        if (encryptedText == null) {
            throw new IllegalArgumentException("encryptedText must not be null");
        }
        byte[] keyBytes = validateKey(key);

        byte[] combined = Base64.getDecoder().decode(encryptedText);
        if (combined.length < IV_LENGTH) {
            throw new IllegalArgumentException("invalid ciphertext: too short to contain IV");
        }

        byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
        byte[] encrypted = Arrays.copyOfRange(combined, IV_LENGTH, combined.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, AES), new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static byte[] validateKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("key must be 16/24/32 bytes (128/192/256 bits), got " + keyBytes.length);
        }
        return keyBytes;
    }
}
