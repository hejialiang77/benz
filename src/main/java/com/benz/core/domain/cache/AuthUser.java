package com.benz.core.domain.cache;

/**
 * 用户登陆信息
 *
 * @ClassName AuthUser
 * @Author jlhe
 * @Date 2019/12/2
 * @Version 1.0
 */
public class AuthUser {
    /**
     *
     */
    private static final long serialVersionUID = -4999587984551423957L;

    /**
     * 登录令牌
     */
    private String loginToken;

    /**
     * 用户Id-对应操作员ID
     */
    private String userId;

    /**
     * 用户信息
     * json
     */
    private String userInfo;
}
