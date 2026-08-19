package com.my.work.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES 加解密工具类（GCM 模式）.
 *
 * <p>使用 AES/GCM/NoPadding 进行加解密，GCM 为认证加密模式，
 * 同时保证数据的机密性与完整性，替代不安全的 ECB 模式。</p>
 *
 * <p>安全特性：</p>
 * <ul>
 *   <li>每次加密生成随机 IV（12 字节），无需额外存储，IV 与密文一同返回</li>
 *   <li>密钥由调用方传入，禁止硬编码密钥</li>
 *   <li>密钥长度支持 128 / 192 / 256 位（16 / 24 / 32 字节）</li>
 *   <li>返回格式：Base64(IV + 密文)</li>
 * </ul>
 */
public final class AesGcmUtils {

    /** AES 算法名. */
    private static final String AES_ALGORITHM = "AES";

    /** 加密转换模式：GCM 认证加密，NoPadding. */
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";

    /** GCM 认证标签长度（位），推荐 128. */
    private static final int GCM_TAG_LENGTH_BITS = 128;

    /** GCM 推荐 IV 长度（字节），固定 12 字节. */
    private static final int GCM_IV_LENGTH_BYTES = 12;

    /** 安全随机数生成器，用于生成 IV. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /** 工具类禁止实例化. */
    private AesGcmUtils() {
    }

    /**
     * 加密数据.
     *
     * <p>生成随机 IV，使用 AES-GCM 加密，返回 Base64(IV + 密文)。</p>
     *
     * @param plainText 待加密的明文
     * @param aesKey    AES 密钥，长度必须为 16 / 24 / 32 字节（UTF-8 编码后）
     * @return Base64 编码的加密结果，格式为 IV + 密文
     * @throws IllegalArgumentException 参数为 null 或密钥长度不合法时抛出
     * @throws Exception                加密过程中的其他异常
     */
    public static String encrypt(String plainText, String aesKey) throws Exception {
        // 参数校验
        if (plainText == null) {
            throw new IllegalArgumentException("明文不能为 null");
        }
        if (aesKey == null) {
            throw new IllegalArgumentException("密钥不能为 null");
        }
        byte[] keyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
        validateKeyLength(keyBytes);

        // 每次加密生成随机 IV，保证相同明文产生不同密文
        byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
        SECURE_RANDOM.nextBytes(iv);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // 组合结果：IV + 密文
        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);

        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 解密数据.
     *
     * <p>解密 {@link #encrypt(String, String)} 方法产生的密文，
     * 自动从密文中提取 IV 与认证标签。</p>
     *
     * @param encryptedText Base64 编码的密文，格式为 IV + 密文
     * @param aesKey        AES 密钥，长度必须为 16 / 24 / 32 字节（UTF-8 编码后）
     * @return 解密后的明文
     * @throws IllegalArgumentException 参数为 null 或数据长度不合法时抛出
     * @throws Exception                密钥错误、数据被篡改或解密失败时抛出
     */
    public static String decrypt(String encryptedText, String aesKey) throws Exception {
        // 参数校验
        if (encryptedText == null) {
            throw new IllegalArgumentException("密文不能为 null");
        }
        if (aesKey == null) {
            throw new IllegalArgumentException("密钥不能为 null");
        }
        byte[] keyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
        validateKeyLength(keyBytes);

        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        if (decoded.length <= GCM_IV_LENGTH_BYTES) {
            throw new IllegalArgumentException("密文数据长度不合法，无法提取 IV");
        }

        // 提取 IV 与密文
        byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
        System.arraycopy(decoded, 0, iv, 0, iv.length);

        byte[] encryptedData = new byte[decoded.length - GCM_IV_LENGTH_BYTES];
        System.arraycopy(decoded, GCM_IV_LENGTH_BYTES, encryptedData, 0, encryptedData.length);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * 校验密钥长度.
     *
     * <p>AES 支持 128 / 192 / 256 位密钥，对应 16 / 24 / 32 字节。</p>
     *
     * @param keyBytes 密钥字节数组
     * @throws IllegalArgumentException 密钥长度不合法时抛出
     */
    private static void validateKeyLength(byte[] keyBytes) {
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException(
                    "AES 密钥长度必须为 16/24/32 字节（128/192/256 位），当前为 " + keyBytes.length + " 字节");
        }
    }
}
