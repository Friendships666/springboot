package com.smile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @作者 liutao
 * @时间 2020/3/5 23:58
 * @描述
 */
@SpringBootApplication
public class ProduceApp {

    public static void main(String[] args) {
        SpringApplication.run(ProduceApp.class, args);
        System.out.println("*****************消息生产者已启动***************");
    }
}
