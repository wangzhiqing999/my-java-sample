package com.my.work.controller;

import com.my.work.config.ConfigData;
import com.my.work.sec.ECCCrypto;
import com.my.work.sec.ECCKeyReader;
import com.my.work.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 简单的、测试用的控制器.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;


    @Autowired
    private ConfigData configData;


    /**
     * 单纯的测试.
     * @return
     */
    @RequestMapping("/get")
    public String get() {
        testService.test();
        return "success";
    }





    /**
     * 测试一个加密的处理.
     *
     * 一般情况下，不使用，这里时单纯为了测试解密的处理，才提供加密的接口。
     * 加密出来结果了， 然后去测试解密的接口.
     *
     * @param originalText
     * @return
     */
    @PostMapping("/encrypt")
    public String encrypt(@RequestBody String originalText) {

        try {
            // 读取密钥
            PublicKey publicKey = ECCKeyReader.readPublicKeyFromString(configData.getPublicKeyPem());


            System.out.println("原始文本: " + originalText);

            String encryptedText = ECCCrypto.encrypt(originalText, publicKey);
            System.out.println("加密后: " + encryptedText);

            return encryptedText;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }



    /**
     * 测试一个解密的处理.
     *
     * 业务逻辑
     * 客户端，使用公钥， 加密一个数据， 然后， Base64编码， 将整个 字符串， POST 到服务器接口上.
     * 服务端，接收到客户端提交过来的数据后， 使用 私钥，解密数据。
     *
     * @param encryptedData
     * @return
     */
    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String encryptedData) {

        try {
            // encryptedData 就是加密后的数据（可能是Base64编码的字符串）
            // 读取密钥
            PrivateKey privateKey = ECCKeyReader.readPrivateKeyFromString(configData.getPrivateKeyPem());

            // 后续可以进行解密处理

            // 处理解密后的数据
            String decryptedText = ECCCrypto.decrypt(encryptedData, privateKey);
            System.out.println("解密后: " + decryptedText);

            return decryptedText;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    /**
     * 测试一个 先解密， 后调用存储过程的处理.
     * @param encryptedData
     * @return
     */
    @PostMapping("/savelog")
    public String saveLogData(@RequestBody String encryptedData) {

        try {

            testService.saveLogData(encryptedData);

            return  "success";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
