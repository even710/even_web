package com.ssm.utils;

import com.ssm.bean.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/18
 */
public class Md5Util {
    private RandomNumberGenerator randomNumberGenerator =
            new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private final int hashIterations = 1024;

    //温馨提示：记得在注册中密码存入数据库前也记得加密哦，提供一个utils方法
//进行shiro加密，返回加密后的结果
    public static String md5(String pass,String saltSource) {
        String hashAlgorithmName = "MD5";
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, pass, salt, hashIterations);
        return result.toString();
    }

    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        System.out.println(new SimpleHash(algorithmName, user.getPassword(), new Md5Hash(user
                .getSalt()), hashIterations));
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), new Md5Hash(user
                .getSalt()), hashIterations).toHex();
        user.setPassword(newPassword);
    }
}
