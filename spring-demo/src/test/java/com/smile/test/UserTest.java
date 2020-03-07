package com.smile.test;

import com.smile.App;
import com.smile.mapper.UserMapper;
import com.smile.pojo.Login;
import com.smile.pojo.User;
import com.smile.service.LoginService;
import com.smile.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.java2d.pipe.AlphaPaintPipe;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/3 23:39
 * @描述
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void test(){
        List<User> list = userMapper.selectUserList();
        if(list != null && list.size() > 0){
            for(User user : list){
                System.out.println("用户信息：" + user);
            }
        }
    }


    @Test
    public void insertTest(){
        userMapper.insertUserElement(new User().setName("笑话").setNo("456123"));
    }



}
