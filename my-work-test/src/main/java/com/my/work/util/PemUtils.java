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

    public static PublicKey readPublicKeyFromFile(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPublicKey(bytes, algorithm);
    }

    public static PrivateKey readPrivateKeyFromFile(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPrivateKey(bytes, algorithm);
    }


    public static PrivateKey readPrivateKeyFromString(String pemString, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEMString(pemString);
        return PemUtils.getPrivateKey(bytes, algorithm);
    }

}
