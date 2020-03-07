package com.smile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @作者 liutao
 * @时间 2020/3/5 23:56
 * @描述
 */
@SpringBootApplication
public class CustomerApp {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApp.class, args);
        System.out.println("*****************消费者队列已启动***************");
    }
}
