package com.ssm.mapper;

import com.ssm.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserMapperTest {

    @Autowired
    public UserMapper userMapper;

    @Test
    public void listAllRoleByUserTest() {
        User user = userMapper.getUserWithRoleAndPermission("W0102549");
        System.out.println(user);
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setPassword("123");
        user.setUser("W0103470");
        user.setSalt("123");
        userMapper.insert(user);
    }

    @Test
    public void isExitUser() {
        System.out.println(userMapper.isExitUser("admin"));
    }

}
