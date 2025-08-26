package com.my.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

/**
 * application
 */
@SpringBootApplication
@EnableAsync // 开启异步方法支持
public class Application {

    public static void main(String[] args) {
        System.out.println("start at " + LocalDateTime.now());
        SpringApplication.run(Application.class, args);
    }
}