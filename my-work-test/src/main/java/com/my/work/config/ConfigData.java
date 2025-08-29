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

}
