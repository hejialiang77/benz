package com.benz.core.controller;

import com.benz.core.common.constants.enums.RetCodeEnum;
import com.benz.core.common.utils.BizException;
import com.benz.core.domain.parameter.ResultModel;
import com.benz.core.domain.vo.LoginVO;
import com.benz.core.service.LoginLocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 *
 * @ClassName LoginController
 * @Author jlhe
 * @Date 2019/12/6
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginLocalService loginLocalService;


    @RequestMapping("doLogin")
    public ResultModel<String> login(LoginVO loginVO, HttpServletResponse response) {

        try {
            log.info(loginVO.getUserName());
            String page = loginLocalService.login(response);
            //登陆状态设置
            return new ResultModel<>(page  , RetCodeEnum.MID_SUCC);
        } catch (BizException e) {
            return new ResultModel<>(e.getCode(), e.getMsg());
        }
    }

}
