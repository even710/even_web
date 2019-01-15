package com.ssm.service;

import com.ssm.bean.User;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */


public interface UserService {
    boolean loginUser(User user);

    User getUserByName(String userName);

    void insertUser(User user);

    boolean isExitUser(String userName);

    User getUserWithRoleAndPermission(String userName);

}
