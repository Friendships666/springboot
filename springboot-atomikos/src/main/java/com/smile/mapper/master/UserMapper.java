package com.smile.mapper.master;

import com.smile.pojo.User;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/5 21:50
 * @描述
 */
public interface UserMapper {

    public List<User> selectUserList();

    int insertUserElement(User user);
}
