package com.benz.core.domain.parameter;

import com.benz.core.common.constants.enums.RetCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultModel<T> implements Serializable {
    private static final long serialVersionUID = -7283984787315189014L;
    private Integer count;
    private T data;
    private String code;
    private String msg;
    private List<String> permissionCodes;

    public ResultModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(T data, RetCodeEnum retCodeEnum) {
        this.data = data;
        this.code = retCodeEnum.name();
        this.msg = retCodeEnum.getDesc();
    }

    public ResultModel(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(T data, String code, String msg, int count) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.count = count;
    }

}