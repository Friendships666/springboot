package com.smile.pojo;

import java.io.Serializable;

/**
 * 用户信息
 */
public class User implements Serializable {

    private Integer id;
    private String name;
    private String no;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getNo() {
        return no;
    }

    public User setNo(String no) {
        this.no = no;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", no='" + no + '\'' +
                '}';
    }
}
