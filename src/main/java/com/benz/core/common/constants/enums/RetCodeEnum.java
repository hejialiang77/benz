package com.benz.core.common.constants.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RetCodeEnum {


    SYSTEM_ERROR("999999","系统异常"),
    MID_SUCC("000000","交易成功"),
    TOKEN_EXPIRED("200000","令牌过期"),
    PARAM_ILLEGAL("100000","参数非法"),
    ;

    String code;
    String desc;

}
