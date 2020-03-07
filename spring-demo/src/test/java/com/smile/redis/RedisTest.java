package com.smile.redis;

import com.smile.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @作者 liutao
 * @时间 2020/3/5 13:04
 * @描述
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","享学课堂真好");
        System.out.println("结果：" + valueOperations.get("name"));

    }


}
