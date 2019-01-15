package com.ssm.utils;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/21
 */
@Component
public class RedisCache implements Cache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String id;

//    public RedisCache(String id){
//
//    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {

    }

    @Override
    public Object getObject(Object key) {
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
