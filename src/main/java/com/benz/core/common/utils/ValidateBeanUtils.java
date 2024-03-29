package com.benz.core.common.utils;

import com.benz.core.common.constants.enums.RetCodeEnum;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 参数校验工具类
 * 
 * @author zhangkun
 * @version $Id: ValidateBeanUtils.java, v 0.1 2017年9月5日 下午6:21:16 zhangkun Exp $
 */
public class ValidateBeanUtils {

    /**
     * 注解参数校验
     * 
     * @param object object
     */
    public static void validateAnnotation(Object object) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure().addProperty("hibernate.validator.fail_fast", "true")
            .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        // 是否有检验失败
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            // 检验失败消息
            throw new BizException(RetCodeEnum.PARAM_ILLEGAL, constraintViolation.getMessage());
        }
    }

}