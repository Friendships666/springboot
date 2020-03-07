package com.smile.mapper;

import com.smile.pojo.Login;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:27
 * @描述
 */
public interface LoginMapper {

    List<Login> selectLoginList();
}
