package com.smile.pojo;

import java.io.Serializable;

/**
 * @作者 liutao
 * @时间 2020/3/3 23:28
 * @描述
 */
public class Login implements Serializable {
    private Integer id;
    private String loginName;
    private String password;

    public Integer getId() {
        return id;
    }

    public Login setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public Login setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Login setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
