package com.ssm;

import com.ssm.bean.User;
import com.ssm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RedisTest {

    @Test
    public void userServiceCacheTest(){
        User user =  userService.getUserWithRoleAndPermission("W0102549");
        System.out.println(user);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("服务正在运行：" + jedis.ping());
        jedis.set("runoobKey", "www.runoob.com");
        System.out.println("redis 存储的字符串为：" + jedis.get("runoobKey"));
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 2);
        for (String aList : list) {
            System.out.println("列表项为: " + aList);
        }

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

    }


    @Test
    public void testSpringRedis(){
//        stringRedisTemplate.delete("");
        stringRedisTemplate.opsForValue().set("myStr","http");
        stringRedisTemplate.opsForValue().set("myStr1","http");
    }


}
