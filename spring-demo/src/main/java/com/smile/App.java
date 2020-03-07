package com.smile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.smile.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("*****************项目已启动***************");
    }

}
