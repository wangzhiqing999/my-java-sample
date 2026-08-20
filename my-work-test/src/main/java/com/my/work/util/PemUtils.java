package com.my.work.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * PEM 密钥文件解析工具类.
 *
 * <p>基于 BouncyCastle 的 {@link PemReader} 解析 PEM 格式密钥，
 * 支持 X.509 公钥（SubjectPublicKeyInfo）与 PKCS#8 私钥（PrivateKeyInfo）。</p>
 */
public final class PemUtils {

    private PemUtils() {
    }

    private static byte[] parsePEMFile(File pemFile) throws IOException {
        if (!pemFile.isFile() || !pemFile.exists()) {
            throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath()));
        }
        PemReader reader = new PemReader(new FileReader(pemFile));
        PemObject pemObject = reader.readPemObject();
        byte[] content = pemObject.getContent();
        reader.close();
        return content;
    }



    private static byte[] parsePEMString(String pemString) throws IOException {
        PemReader reader = new PemReader(new StringReader(pemString));
        PemObject pemObject = reader.readPemObject();
        byte[] content = pemObject.getContent();
        reader.close();
        return content;
    }




    private static PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Could not reconstruct the public key, the given algorithm could not be found: " + algorithm, e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("Could not reconstruct the public key", e);
        }
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Could not reconstruct the private key, the given algorithm could not be found: " + algorithm, e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("Could not reconstruct the private key", e);
        }
    }

    /**
     * 从 PEM 文件读取公钥.
     *
     * @param filepath  PEM 文件路径
     * @param algorithm 密钥算法（如 "RSA"、"EC"）
     * @return 解析后的公钥对象
     * @throws IOException 文件不存在、读取失败或 PEM 解析失败时抛出
     */
    public static PublicKey readPublicKeyFromFile(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPublicKey(bytes, algorithm);
    }

    /**
     * 从 PEM 文件读取私钥.
     *
     * @param filepath  PEM 文件路径
     * @param algorithm 密钥算法（如 "RSA"、"EC"）
     * @return 解析后的私钥对象
     * @throws IOException 文件不存在、读取失败或 PEM 解析失败时抛出
     */
    public static PrivateKey readPrivateKeyFromFile(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPrivateKey(bytes, algorithm);
    }


    /**
     * 从 PEM 字符串读取私钥.
     *
     * @param pemString PEM 格式的私钥字符串
     * @param algorithm 密钥算法（如 "RSA"、"EC"）
     * @return 解析后的私钥对象
     * @throws IOException PEM 解析失败时抛出
     */
    public static PrivateKey readPrivateKeyFromString(String pemString, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMString(pemString);
        return PemUtils.getPrivateKey(bytes, algorithm);
    }

}
