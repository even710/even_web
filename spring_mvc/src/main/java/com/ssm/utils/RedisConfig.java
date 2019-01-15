package com.ssm.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/21
 */
@Configuration
public class RedisConfig {
//    /**
//     * 连接池配置信息
//     *
//     * @return
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); //最大连接数
//        jedisPoolConfig.setMaxTotal(100); //最小空闲连接数
//        jedisPoolConfig.setMinIdle(20); //当池内没有可用的连接时，最大等待时间
//        jedisPoolConfig.setMaxWaitMillis(10000); //------其他属性根据需要自行添加-------------
//        return jedisPoolConfig;
//    }
//
//    /**
//     * Redis连接池工厂类
//     * @param jedisPoolConfig
//     * @return
//     */
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        /*创建单例模式的配置*/
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        /*设置redis服务器的host或ip地址*/
//        redisStandaloneConfiguration.setHostName("localhost");
//        /*设置redis服务器的端口*/
//        redisStandaloneConfiguration.setPort(6379);
//        /*设置使用默认的连接池*/
//        redisStandaloneConfiguration.setDatabase(0);
//        /*设置redis的密码*/
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("19940710"));
//        /*获取默认连接池构造*/
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        /*修改连接池配置*/
//        jedisPoolingClientConfigurationBuilder.poolConfig(jedisPoolConfig);
//        /*通过构造器来构造jedis客户端配置*/
//        JedisClientConfiguration jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.build();
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
//
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager.create(connectionFactory);
//    }
//
//    /*字符串序列化策略*/
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        return new StringRedisTemplate(redisConnectionFactory);
//    }

}

