package com.my.work.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="my.work.config")
public class ConfigData {


    /**
     * 私钥.
     */
    private String privateKeyPem;


    /**
     * 公钥.
     */
    private String publicKeyPem;




    /**
     * 这个是模拟一个 配置.
     * 默认值的配置.
     * 也就是，这个配置， 在配置文件中， 没有设置.
     *
     * 然后，测试后面使用这个 配置的时候， 能否获取到 配置的默认值.
     */
    private boolean testBooleanDefaultValue = true;

    /**
     * 这个是模拟一个 配置.
     * 默认值的配置.
     * 也就是，这个配置， 在配置文件中， 没有设置.
     *
     * 然后，测试后面使用这个 配置的时候， 能否获取到 配置的默认值.
     */
    private int testIntDefaultValue = 1024;

    /**
     * 这个是模拟一个 配置.
     * 默认值的配置.
     * 也就是，这个配置， 在配置文件中， 没有设置.
     *
     * 然后，测试后面使用这个 配置的时候， 能否获取到 配置的默认值.
     */
    private String testStringDefaultValue = "Default String value";



    // 下面这3个， 是类里面定义了默认值， 但是， 配置文件中， 也配置了新的数值。
    private boolean testBooleanDefaultValue2 = true;
    private int testIntDefaultValue2 = 1024;
    private String testStringDefaultValue2 = "Default String value";
}
