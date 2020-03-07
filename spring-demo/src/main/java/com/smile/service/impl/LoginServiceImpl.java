package com.smile.service.impl;

import com.smile.druid.DataSourceCut;
import com.smile.druid.DataSourceType;
import com.smile.mapper.LoginMapper;
import com.smile.pojo.Login;
import com.smile.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/4 0:30
 * @描述
 */
@Service
public class LoginServiceImpl implements LoginService {

    //@Resource
    @Autowired
    private LoginMapper loginMapper;

    @DataSourceCut(DataSourceType.SLAVE)
    @Override
    public List<Login> selectLoginList() {
        return loginMapper.selectLoginList();
    }
}
