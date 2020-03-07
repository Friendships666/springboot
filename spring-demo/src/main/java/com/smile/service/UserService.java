package com.smile.service;

import com.smile.pojo.User;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:29
 * @描述
 */
public interface UserService {

    public List<User> selectUserList();

    int insertUserElement(User user);
}
