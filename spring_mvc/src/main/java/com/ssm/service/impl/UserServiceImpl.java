package com.ssm.service.impl;

import com.ssm.bean.User;
import com.ssm.mapper.UserMapper;
import com.ssm.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
@Service("userService")
@CacheConfig(cacheNames = "User")//指定当前servier下的所有用到缓存的方法对应redis中缓存名为User
public class UserServiceImpl implements UserService
{
    @Resource
    private UserMapper userMapper;

    @Override
    @Cacheable()
    public User getUserByName(String userName)
    {
//        return userMapper.getUserByName(userName);
        return null;
    }

    @Override
    public boolean loginUser(User user)
    {
//        User user1 = userMapper.getUserByName(user.getUser());
//        return Md5Util.md5(user.getPassword(), user1.getCredentialsSalt()).equals(user1.getPassword());
        return false;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void insertUser(User user)
    {
        userMapper.insert(user);
    }


    @Override
    @Cacheable()
    public boolean isExitUser(String userName)
    {
//        return "1".equals(userMapper.isExitUser(userName));
        return false;
    }

    @Override
    @Cacheable()
    public User getUserWithRoleAndPermission(String userName)
    {
//        return userMapper.getUserWithRoleAndPermission(userName);
        return null;
    }
}
