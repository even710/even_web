package com.ssm.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        String username = (String)token.getPrincipal();
//        //retry count + 1
//        Element element = passwordRetryCache.get(username);
//        if(element == null) {
//            element = new Element(username , new AtomicInteger(0));
//            passwordRetryCache.put(element);
//        }
//        AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();
//        if(retryCount.incrementAndGet() > 5) {
//            //if retry count > 5 throw
//            throw new ExcessiveAttemptsException();
//        }
//
//        boolean matches = super.doCredentialsMatch(token, info);
        return super.doCredentialsMatch(token, info);
    }
}
