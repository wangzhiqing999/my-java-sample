package com.my.work.sec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;


/**
 * Deepseek 生成的 加密解密的代码.
 */
public class ECCCrypto {

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    // ECC加密（实际使用ECDH进行密钥协商，然后用AES加密）
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        // 生成临时的ECC密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(new ECGenParameterSpec("secp256k1"));
        KeyPair tempKeyPair = keyGen.generateKeyPair();
        PrivateKey tempPrivateKey = tempKeyPair.getPrivate();

        // 执行ECDH密钥协商
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(tempPrivateKey);
        keyAgreement.doPhase(publicKey, true);

        // 生成共享密钥
        byte[] sharedSecret = keyAgreement.generateSecret();
        SecretKey aesKey = deriveAESKey(sharedSecret);

        // 使用AES加密数据
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] iv = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());

        // 将临时公钥、IV和加密数据一起返回
        byte[] tempPublicKey = tempKeyPair.getPublic().getEncoded();

        // 组合数据：临时公钥长度 + 临时公钥 + IV长度 + IV + 加密数据
        byte[] result = new byte[2 + tempPublicKey.length + 2 + iv.length + encryptedData.length];

        // 写入临时公钥长度和公钥
        result[0] = (byte) ((tempPublicKey.length >> 8) & 0xFF);
        result[1] = (byte) (tempPublicKey.length & 0xFF);
        System.arraycopy(tempPublicKey, 0, result, 2, tempPublicKey.length);

        // 写入IV长度和IV
        int offset = 2 + tempPublicKey.length;
        result[offset] = (byte) ((iv.length >> 8) & 0xFF);
        result[offset + 1] = (byte) (iv.length & 0xFF);
        System.arraycopy(iv, 0, result, offset + 2, iv.length);

        // 写入加密数据
        System.arraycopy(encryptedData, 0, result, offset + 2 + iv.length, encryptedData.length);

        return Base64.getEncoder().encodeToString(result);
    }

    // ECC解密
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] data = Base64.getDecoder().decode(encryptedText);

        // 读取临时公钥
        int tempPublicKeyLength = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
        byte[] tempPublicKeyBytes = new byte[tempPublicKeyLength];
        System.arraycopy(data, 2, tempPublicKeyBytes, 0, tempPublicKeyLength);

        // 从字节数组重建临时公钥
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PublicKey tempPublicKey = keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(tempPublicKeyBytes));

        // 读取IV
        int offset = 2 + tempPublicKeyLength;
        int ivLength = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
        byte[] iv = new byte[ivLength];
        System.arraycopy(data, offset + 2, iv, 0, ivLength);

        // 读取加密数据
        byte[] encryptedData = new byte[data.length - (offset + 2 + ivLength)];
        System.arraycopy(data, offset + 2 + ivLength, encryptedData, 0, encryptedData.length);

        // 执行ECDH密钥协商
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(tempPublicKey, true);

        // 生成共享密钥
        byte[] sharedSecret = keyAgreement.generateSecret();
        SecretKey aesKey = deriveAESKey(sharedSecret);

        // 使用AES解密数据
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, new javax.crypto.spec.GCMParameterSpec(128, iv));

        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    // 从共享密钥派生AES密钥
    private static SecretKey deriveAESKey(byte[] sharedSecret) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(sharedSecret);
        return new SecretKeySpec(keyBytes, "AES");
    }
}