package com.ssm.mapper;

import com.ssm.bean.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RoleMapperTest {
    @Autowired
    public RoleMapper roleMapper;

    @Test
    public void listAllRoleByUser() {
        List<Role> role = roleMapper.listAllRoleByUser("W0102549");
        System.out.println(role);
    }
}
