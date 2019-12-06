package com.benz.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.benz.core.common.constants.OperatorConstants;
import com.benz.core.common.constants.enums.RetCodeEnum;
import com.benz.core.domain.cache.AuthUser;
import com.benz.core.common.utils.*;
import com.benz.core.common.utils.entities.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * 登录过滤器
 *
 * @author jlhe
 * @version $Id: LoginFilter.java, v 0.1 2019年1月11日 下午3:45:38 jlhe Exp $
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
@Order(-1)
@Slf4j
public class LoginFilter implements Filter {


    // 免登录列表
    @Value("${login.exclude.url.list}")
    private String                   loginExcludeUrlList;


    // 免登录链接，登录情况下强制跳转
    @Value("${login.exclude.redirect.list}")
    private String                   loginExcludeRedirectList;

    @Value("${spring.application.name}")
    private String                   appName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        try {
            String requestUrl = RequestUtil.getUrl((HttpServletRequest) request);
            log.info("LoginFilter -url- {} -uri- {} -requestUrl- {} -servletPath- {} ", requestUrl, servletRequest.getRequestURI(), servletRequest
                .getRequestURL().toString(), servletRequest.getServletPath());
            TraceUtil.setClientIP(RequestUtil.getClientIp(servletRequest));
            TraceUtil.setCorrelationID(UUID.randomUUID().toString());
            log.info("loginExcludeUrlList-- {}", loginExcludeUrlList);
            // 需要登录标识
            boolean needLogin = true;
            // 静态资源及页面访问不拦截
            if (requestUrl.matches(".*\\.(jpg|swf|png|gif|ico|svg|js|css|xls|xlsx|ttf|woff)$")) {
                needLogin = false;
            } else if (loginExcludeUrlList.contains(requestUrl)) {
                needLogin = false;
            } else if (requestUrl.contains("/static/") || requestUrl.contains("swagger")
                       || requestUrl.contains("api-docs")) {
                needLogin = false;
            }
            log.info("needLogin-- {}", needLogin);

            String loginToken = CookieUtil.getCookie(servletRequest,
                OperatorConstants.COOKIE_LOGIN_TOKEN);
            // 登录验证
            if (needLogin) {
                // 校验登录令牌是否存在
                Assert.hasText(loginToken, OperatorConstants.TOKEN_EXPIRED);
                // 校验服务端是否已登录
                checkLoginToken(loginToken, servletRequest, (HttpServletResponse) response);
                sendVisitLogMsg(requestUrl);
            } else if (!"/".equals(requestUrl) && loginExcludeRedirectList.contains(requestUrl)) {
                if (StringUtils.hasText(loginToken)) {
                    // 免登录页面，在已登录的情况下，跳转首页
                }
            }
            chain.doFilter(request, response);
        } catch (BizException ex) {
            log.info("业务异常{},{}", ex.getCode(), ex.getMessage());
            redirectPage(servletRequest, (HttpServletResponse) response, ex.getCode(),
                ex.getMessage());
        } catch (Exception ex) {
            log.error("系统异常", ex);
            redirectPage(servletRequest, (HttpServletResponse) response,
                RetCodeEnum.SYSTEM_ERROR.name(),
                RetCodeEnum.SYSTEM_ERROR.getDesc());
        } finally {
            TraceUtil.clear();
            UserContextUtil.removeUserContext();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断服务端是否已登录
     * 
     * @param loginToken loginToken
     * @return
     */
    private void checkLoginToken(String loginToken, HttpServletRequest request,
                                 HttpServletResponse response) {
        AuthUser authUser = checkLoginStatus(loginToken);
        // 校验服务端是否登录
        Assert.notNull(authUser, RetCodeEnum.TOKEN_EXPIRED.name());
        // 已登录，设置登录信息
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(authUser,loginUser);
        // 设置登录用户上下文
        UserContextUtil.setUserContext(loginUser);
    }

    /**
     * 查询单点登录状态
     * TODO 先写死哈哈
     * 
     * @param loginToken
     * @return
     */
    private AuthUser checkLoginStatus(String loginToken) {
        AuthUser authUser = new AuthUser();
        authUser.setLoginToken(loginToken);
        authUser.setUserId("hjl");
        authUser.setUserInfo("{\"name\":\"何嘉良\"}");
        return authUser;
    }

    /**
     * 校验访问链接是否有权限
     * 
     * @param requestUrl
     */
    private void checkPermissions(String requestUrl) {
        LoginUser loginUser = UserContextUtil.getUserContext();
        // 如果有关联企业
        UserContextUtil.setUserContext(loginUser);
    }

    /**
     * 发送访问日志消息
     */
    private void sendVisitLogMsg(String visitUrl) {
    }

    /**
     * 跳转登陆页
     * 
     * @param request
     * @param response
     */
    private void redirectPage(HttpServletRequest request, HttpServletResponse response,
                              String code, String msg) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", code);
            jsonResult.put("msg", msg);
            out = response.getWriter();
            out.println(JSON.toJSONString(jsonResult));
        } catch (Exception ex) {
            log.error("返回流处理异常", ex);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

}
