package com.ssm.vo;

import java.io.Serializable;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
public class UserVO implements Serializable{
    private Integer userid;
    private String user;
    private String password;
    private String qq;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
