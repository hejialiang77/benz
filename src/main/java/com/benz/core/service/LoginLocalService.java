package com.benz.core.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 本地登陆服务
 *
 * @ClassName LoginLocalService
 * @Author jlhe
 * @Date 2019/12/6
 * @Version 1.0
 */
@Service
public class LoginLocalService {

    public String login(){
        //检查一下登陆用户名,用户密码,返回一个token
        //放入缓存
        return UUID.randomUUID().toString();
    }
}
