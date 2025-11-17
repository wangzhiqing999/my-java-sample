package com.my.work.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {


    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static final String AES = "AES";

    public static final byte[] IV = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 6, 5, 4, 3, 2, 1};

    public static String doEncrypt(String text, String key) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
        IvParameterSpec ivps = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        var data = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(data);
    }

    public static String doDecrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
        IvParameterSpec ivps = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        var data = cipher.doFinal(Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8)));
        return new String(data, StandardCharsets.UTF_8);
    }


    public static void main(String[] args)  throws Exception {
        String phone = "lR3q9H52CH3NqOPlaAFmUQ==";
        System.out.println(doDecrypt(phone, "lRIOmoN35bnk5haOwriExDGmVBmyYMoo"));

    }


}
