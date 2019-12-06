package com.benz.core.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：lihp
 * @date ：Created in 2019/1/23 10:10
 * @description：获取请求信息工具类
 */
public class RequestUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }

        if (ip.indexOf(",") >= 0) {
            ip = ip.split(",")[0];
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "localhost";
        }

        return ip;
    }

    public static void main(String[] args) {
        String str = ",1";
        if (",1".indexOf(",") >= 0) {
            System.out.println("--" + str.split(",")[0]);
        }
    }

    public static String getFullUri(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) {
            scheme = request.getHeader("X-Forwarded-For");
        }

        if (scheme == null || !scheme.equals("htpps")) {
            scheme = request.getScheme();
        }

        String uri = scheme
                     + "://"
                     + request.getServerName()
                     + ((!"http".equals(request.getScheme()) || request.getServerPort() != 80)
                        && (!"https".equals(request.getScheme()) || request.getServerPort() != 443) ? ":"
                                                                                                      + request
                                                                                                          .getServerPort()
                         : "") + request.getRequestURI()
                     + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return uri;
    }

    public static String getUrl(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        return url;
    }
}
