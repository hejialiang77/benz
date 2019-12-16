package com.benz.core.service;

import com.benz.core.common.constants.OperatorConstants;
import com.benz.core.common.utils.CookieUtil;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public String login( HttpServletResponse response){
        //检查一下登陆用户名,用户密码,返回index
        //放入缓存
        String token = UUID.randomUUID().toString();
        CookieUtil.addCookie(OperatorConstants.COOKIE_LOGIN_TOKEN,UUID.randomUUID().toString(),  response);
        return "index.html";
    }
}
