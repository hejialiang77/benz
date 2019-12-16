package com.benz.core.common.utils;

import com.benz.core.common.constants.enums.RetCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

/**
 * 自定义业务异常
 * 
 * @author wuliang
 * @version $Id: BizException.java, v 0.1 2018年11月4日 下午4:13:28 wuliang Exp $
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -7815030607374292379L;

    private String            code;

    private String            msg;

    private String            detailMessage;

    public BizException() {
        super();
    }

    public BizException(RetCodeEnum resultCode) {
        super();
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
    }

    public BizException(String code, String message) {
        super();
        this.code = code;
        this.msg = message;
    }

    public BizException(RetCodeEnum resultCode, String detailMessage) {
        super();
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
        this.detailMessage = detailMessage;
    }


    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String getMessage() {
        return StringUtils.isEmpty(detailMessage) ? msg : detailMessage;
    }

    @Override
    public String toString() {

        return "[BizException]" +
                "code:" +
                code +
                ",msg:" +
                msg +
                ",detailMessage:" +
                detailMessage;
    }

}
