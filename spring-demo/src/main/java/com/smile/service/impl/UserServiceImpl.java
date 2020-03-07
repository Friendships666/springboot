package com.smile.service.impl;

import com.smile.mapper.UserMapper;
import com.smile.pojo.User;
import com.smile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:29
 * @描述
 */
@Service
public class UserServiceImpl implements UserService {

    //@Resource
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserList() {
        return userMapper.selectUserList();
    }

    @Override
    public int insertUserElement(User user) {
        return userMapper.insertUserElement(user);
    }
}
