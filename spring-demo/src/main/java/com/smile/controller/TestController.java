package com.smile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 liutao
 * @时间 2020/3/3 23:50
 * @描述
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public String logTest(){
        logger.info("你好，日志测试,info");
        logger.debug("你好，日志测试，debug");
        logger.error("你好，日志测试,error");
        return "Hello";
    }

}
