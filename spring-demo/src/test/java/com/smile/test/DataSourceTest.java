package com.smile.test;

import com.smile.App;
import com.smile.pojo.Login;
import com.smile.pojo.User;
import com.smile.service.LoginService;
import com.smile.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:59
 * @描述
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class DataSourceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @Test
    public void twoDataSource(){
        List<User> list = userService.selectUserList();
        if(list != null && list.size() > 0){
            for(User user : list){
                System.out.println("用户信息：" + user);
            }
        }

        //**************登录信息表************
        List<Login> loginList = loginService.selectLoginList();
        if(loginList != null && loginList.size() > 0){
            for(Login login : loginList){
                System.out.println("登录信息：" + login);
            }
        }
    }
}
