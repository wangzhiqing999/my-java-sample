package com.my.work.controller;

import com.my.work.config.ConfigData;
import com.my.work.model.CommonResult;
import com.my.work.sec.ECCCrypto;
import com.my.work.sec.ECCKeyReader;
import com.my.work.service.ClientService;
import com.my.work.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 注意：这个服务，是有多个实现的，具体使用哪一个实现，配置在 application.yml 文件中的 spring-->profiles-->active 下面.
     */
    @Autowired
    private ClientService clientService;



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
     * 测试保存配置信息.
     * http://localhost:8080/test/saveconfig?code=1&msg=test_message
     * @param code
     * @param msg
     * @return
     */
    @RequestMapping("/saveconfig")
    public String saveConfig(int code, String msg) {
        CommonResult testData = new CommonResult();
        testData.setCode(code);
        testData.setMsg(msg);
        testService.testSaveConfig("TEST", testData);
        return "success";
    }

    /**
     * 测试保存配置信息.
     * http://localhost:8080/test/readconfig
     * @return
     */
    @RequestMapping("/readconfig")
    public String readConfig() {
        CommonResult testData = testService.testLoadConfig("TEST");
        return testData.getMsg();
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


    /**
     * 测试被定时调用的接口
     * @return
     */
    @GetMapping("/daily-task")
    public String dailyTask() {

        // 调用服务，完成 需要定时执行的任务.
        return testService.dailyTask();
    }


    /**
     * 测试默认的配置.
     * 也就是：配置的类里面，定义了属性的初始数值， 但是在配置文件中， 没有定义属性的值。
     * 系统在默认情况下，能正常运行，要具体做调整的时候，再修改配置文件， 针对特定的属性，进行修改配置.
     * @return
     */
    @GetMapping("/config")
    public String testConfig() {
        return testService.testConfig();
    }




    /**
     * 测试的，相同接口，不同实现的处理.
     * 如果更换其它实现的情况下，需要修改 application.yml 配置文件.
     * 配置在 application.yml 文件中的 spring-->profiles-->active 下面.
     *
     *
     * 测试的机制，
     * 先启动项目，然后访问 http://localhost:8080/test/info
     * 得到的是 客户A 的实现。
     *
     *
     * 停止项目，application.yml 文件中的 spring-->profiles-->active 修改为 clientB
     * 再运行项目， 刷新 http://localhost:8080/test/info
     * 得到的是 客户B 的实现。
     *
     * 也就是一套代码， 发布给不同的客户使用，  不同的客户， 又有其自己特有的 业务逻辑， 通过不同的实现，以及配置文件， 来完成， 发布到不同的客户那里，实现特定客户的功能。
     *
     * @return
     */
    @GetMapping("/info")
    public String getClientInfo() {
        return clientService.getClientInfo();
    }


}
