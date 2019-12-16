package com.benz.core.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;


/**
 * @author ：lihp
 * @date ：Created in 2019/1/23 11:18
 * @description：cookie工具类
 */
public class CookieUtil {

    private final static String COOKIE_PATH = "/";
    private final static String COOKIE_DOMAIN = "";

    /**
     * @param response
     * @param name
     * @param value
     * @desc 添加一条新的Cookie信息，默认有效时间为一周
     */
    public static void addCookie(String name, String value, HttpServletResponse response) {
        int maxAge = Integer.MAX_VALUE;
        addCookie(name, value, maxAge, response);
    }

    /**
     * 添加cookie
     *
     * @param name     cookie的key
     * @param value    cookie的value
     *                 ＠param  path path
     * @param maxage   最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name, String value, int maxage, String path,
                                 HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        if (StringUtils.hasText(COOKIE_DOMAIN)) {
            cookie.setDomain(COOKIE_DOMAIN);
        }
        if (maxage != 0) {
            cookie.setMaxAge(maxage);
        }
        cookie.setHttpOnly(true);
        // 线上环境设置启用安全域名校验
//        Environment environment = ApplicationContextUtils.getEnvironment();
//        String profileActive = environment.getRequiredProperty("spring.profiles.active");
//        if (StringUtils.hasText(profileActive) && profileActive.equalsIgnoreCase("online")) {
//            cookie.setSecure(true);
//        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 往根下面存一个cookie
     * * @param name cookie的key
     *
     * @param value    cookie的value
     * @param maxage   最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name, String value,  int maxage,
                                 HttpServletResponse response) {
        addCookie(name, value, maxage, COOKIE_PATH, response);
    }

    /**
     * @param response
     * @param cookie
     * @desc 删除指定Cookie
     */
    public static void removeCookie(HttpServletResponse response, Cookie cookie) {
        String domain = "";
        if (cookie != null) {
            cookie.setPath(COOKIE_PATH);
            cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }
    }

    /**
     * @param response
     * @param name
     * @desc 删除指定名称的Cookie
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0);
//        cookie.setDomain(".benz.com");
        response.addCookie(cookie);
    }

    /**
     * 从cookie值返回cookie值，如果没有返回 null
     *
     * @param request request
     * @param name    name
     * @return cookie的值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

}
