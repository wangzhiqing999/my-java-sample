package com.my.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

/**
 * application
 */
@SpringBootApplication
@EnableAsync // 开启异步方法支持
@EnableScheduling // 开启定时任务
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * 应用启动入口.
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        log.info("start at {}", LocalDateTime.now());
        SpringApplication.run(Application.class, args);
    }
}