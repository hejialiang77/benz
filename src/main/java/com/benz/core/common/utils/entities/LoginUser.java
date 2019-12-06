package com.benz.core.common.utils.entities;


import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

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