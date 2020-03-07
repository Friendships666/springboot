package com.smile.service;

import com.smile.mapper.master.UserMapper;
import com.smile.mapper.slaver.LoginMapper;
import com.smile.pojo.Login;
import com.smile.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @作者 liutao
 * @时间 2020/3/5 22:46
 * @描述
 */
@Service
public class BothService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Transactional
    public void insertBothElement(User user, Login login){
        userMapper.insertUserElement(user);
        // int k = 10/0;
        loginMapper.insertLogin(login);
    }


}
