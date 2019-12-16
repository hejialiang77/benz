package com.benz.core.config;

import com.alibaba.fastjson.JSON;
import com.benz.core.common.utils.TraceUtil;
import com.benz.core.common.utils.ValidateBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * web请求AOP日志
 *
 * @author wuliang
 * @version $Id: WebLogAspect.java, v 0.1 2018年11月4日 下午5:47:28 wuliang Exp $
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    // spring的默认bean的scope是singleton,每次请求使用的aspect都是同一个实例
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.benz.core.controller..*Controller.*(..))")
    private void weblog() {
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) {
        // 记录请求开始时间
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String queryString = request.getQueryString();
        // 经过验证，这里@RequestBody注解的切面会先生效，获取到的args为已经做过json转化的请求参数对象
        Object[] args = joinPoint.getArgs();
        String params = "";
        // 获取请求参数集合并进行遍历拼接
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            params = queryString;
        } else if (args.length > 0) {
            params = JSON.toJSONString(args[0]);
        }
        log.info("{}请求链接{}, 请求报文,{}", TraceUtil.getClientIP(), request.getRequestURL().toString(), params);
        if (args.length > 0 && "POST".equalsIgnoreCase(request.getMethod())) {
            // 校验参数合法性
            ValidateBeanUtils.validateAnnotation(args[0]);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doAfterReturning(Object ret) {
        // 接口响应日志
        log.info("请求耗时:{}ms", (System.currentTimeMillis() - startTime.get()));
    }

}