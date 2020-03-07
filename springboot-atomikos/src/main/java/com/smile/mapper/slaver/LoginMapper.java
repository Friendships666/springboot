package com.smile.mapper.slaver;

import com.smile.pojo.Login;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/5 21:51
 * @描述
 */
public interface LoginMapper {

    List<Login> selectLoginList();

    int insertLogin(Login login);
}
