package com.ssm.shiro;

import com.ssm.bean.Permission;
import com.ssm.bean.Role;
import com.ssm.bean.User;
import com.ssm.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/18
 */
public class LoginAuthorRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;


    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object principal = principalCollection.getPrimaryPrincipal();//获取登录的用户名
        User user = userService.getUserWithRoleAndPermission((String) principal);
        for (Role role : user.getRoleList()) {
            info.addRole(role.getRolename());
        }
        
        for (Permission permission : user.getPermissionList()) {
            info.addStringPermission(permission.getPermissionname());
        }
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1. token 中获取登录的 username! 注意不需要获取password.
        Object principal = authenticationToken.getPrincipal();
        //2. 利用 username 查询数据库得到用户的信息.
        User user = userService.getUserByName((String) principal);
        assert user != null : "找不到用户";
        String pass = user.getPassword();
        //3.设置盐值
        ByteSource credentialsSalt = new Md5Hash(user.getCredentialsSalt());
        //当前 Realm 的name，getName()是父类CachingRealm的方法
        String realmName = getName();

        //返回值实例化
        return new SimpleAuthenticationInfo(principal, pass,
                credentialsSalt, realmName);
    }

    //init-method 配置.
    public void setCredentialMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//MD5算法加密
        credentialsMatcher.setHashIterations(1024);//1024次循环加密
        setCredentialsMatcher(credentialsMatcher);
    }


    //用来测试的算出密码password盐值加密后的结果，下面方法用于新增用户添加到数据库操作的，我这里就直接用main获得，直接数据库添加了，省时间
    public static void main(String[] args) {
        String saltSource = "W01025496becee7aee68284f9bc92c45fcee4dcb";
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }
}
