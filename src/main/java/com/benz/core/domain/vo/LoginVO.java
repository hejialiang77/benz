package com.benz.core.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登陆返回
 *
 * @ClassName LoginVO
 * @Author jlhe
 * @Date 2019/12/6
 * @Version 1.0
 */
@Data
public class LoginVO implements Serializable {

    String userName;

    String password;

    String token;

}
