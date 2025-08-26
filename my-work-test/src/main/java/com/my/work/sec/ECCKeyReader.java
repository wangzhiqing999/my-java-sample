package com.my.work.sec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

/**
 * Deepseek 生成的 读取 密钥的代码.
 */
public class ECCKeyReader {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // 从PEM文件读取私钥
    public static PrivateKey readPrivateKeyFromFile(String filename) throws Exception {
        try (PEMParser pemParser = new PEMParser(new FileReader(filename))) {
            return readPrivateKey(pemParser);
        }
    }

    // 从PEM字符串读取私钥
    public static PrivateKey readPrivateKeyFromString(String privateKeyPem) throws Exception {
        try (PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem))) {
            return readPrivateKey(pemParser);
        }
    }

    private static PrivateKey readPrivateKey(PEMParser pemParser) throws IOException {
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        if (object instanceof PEMKeyPair) {
            PEMKeyPair keyPair = (PEMKeyPair) object;
            return converter.getPrivateKey(keyPair.getPrivateKeyInfo());
        } else if (object instanceof org.bouncycastle.asn1.pkcs.PrivateKeyInfo) {
            return converter.getPrivateKey((org.bouncycastle.asn1.pkcs.PrivateKeyInfo) object);
        } else {
            throw new PEMException("Unsupported private key format: " + object.getClass().getName());
        }
    }

    // 从PEM文件读取公钥
    public static PublicKey readPublicKeyFromFile(String filename) throws Exception {
        try (PEMParser pemParser = new PEMParser(new FileReader(filename))) {
            return readPublicKey(pemParser);
        }
    }

    // 从PEM字符串读取公钥
    public static PublicKey readPublicKeyFromString(String publicKeyPem) throws Exception {
        try (PEMParser pemParser = new PEMParser(new StringReader(publicKeyPem))) {
            return readPublicKey(pemParser);
        }
    }

    private static PublicKey readPublicKey(PEMParser pemParser) throws IOException {
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        if (object instanceof org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) {
            return converter.getPublicKey((org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) object);
        } else if (object instanceof PEMKeyPair) {
            PEMKeyPair keyPair = (PEMKeyPair) object;
            return converter.getPublicKey(keyPair.getPublicKeyInfo());
        } else {
            throw new PEMException("Unsupported public key format: " + object.getClass().getName());
        }
    }

    // 同时读取密钥对
    public static KeyPair readKeyPairFromString(String privateKeyPem) throws Exception {
        try (PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair) {
                PEMKeyPair keyPair = (PEMKeyPair) object;
                PrivateKey privateKey = converter.getPrivateKey(keyPair.getPrivateKeyInfo());
                PublicKey publicKey = converter.getPublicKey(keyPair.getPublicKeyInfo());
                return new KeyPair(publicKey, privateKey);
            } else {
                throw new PEMException("Unsupported key pair format: " + object.getClass().getName());
            }
        }
    }
}