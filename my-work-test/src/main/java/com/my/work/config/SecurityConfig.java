package com.my.work.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

/**
 * 安全相关 Bean 配置.
 *
 * <p>通过 Spring {@code @Bean} 统一注册 BouncyCastle Provider，
 * 替代此前散布在 {@code ECCCrypto} / {@code ECCKeyReader} 中的 {@code static} 块。
 * Spring 容器初始化时注册一次，单例语义保证不重复注册。</p>
 */
@Configuration
public class SecurityConfig {

    /**
     * 注册 BouncyCastle 安全 Provider 并返回单例 Bean.
     *
     * <p>Spring 容器启动时自动调用，将 {@link BouncyCastleProvider} 加入
     * {@link Security} 全局注册表。此后 {@code getInstance("EC", "BC")}
     * 等调用可直接使用 "BC" Provider。</p>
     *
     * @return BouncyCastle Provider 单例
     */
    @Bean
    BouncyCastleProvider bouncyCastleProvider() {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        if (Security.getProvider("BC") == null) {
            Security.addProvider(provider);
        }
        return provider;
    }
}
