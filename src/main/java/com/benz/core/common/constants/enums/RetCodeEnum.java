package com.benz.core.common.constants.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RetCodeEnum {


    SYSTEM_ERROR("系统异常"),
    MID_SUCC("交易成功"),
    TOKEN_EXPIRED("令牌过期"),
    ;
    String desc;



}
