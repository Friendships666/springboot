package com.smile.service;

import com.smile.pojo.Login;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:30
 * @描述
 */
public interface LoginService {
    List<Login> selectLoginList();
}
