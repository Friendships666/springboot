package com.smile;


import com.smile.mapper.master.UserMapper;
import com.smile.mapper.slaver.LoginMapper;
import com.smile.pojo.Login;
import com.smile.pojo.User;
import com.smile.service.BothService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class SpringbootAtomikosApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private BothService bothService;


    @Test
    public void contextLoads() {
        List<User> list = userMapper.selectUserList();
        if(list != null && list.size() > 0){
            for(User user : list){
                System.out.println("用户信息：" + user);
            }
        }

        List<Login> loginList = loginMapper.selectLoginList();
        if(loginList != null && loginList.size() > 0){
            for(Login login : loginList){
                System.out.println("登录信息：" + login);
            }
        }
    }


    @Test
    public void bothInsert(){
        User user = new User().setName("xiaoming22").setNo("4895uwieuwi222");
        Login login = new Login().setLoginName("admin222").setPassword("admin123");
        bothService.insertBothElement(user, login);
    }

}
