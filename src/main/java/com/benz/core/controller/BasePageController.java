/**
 * Copyright © 2004-2018 LianlianPay.All Rights Reserved.
 * <p>
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
/**
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.benz.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面路由
 *
 * @author wuliang
 * @version $Id: PageController.java, v 0.1 2018年11月7日 上午11:20:18 wuliang Exp $
 */
@Controller
@Slf4j
public class BasePageController {

    @GetMapping(value = "/")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }
    @GetMapping(value = "/pages/home.html")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/pages/home");
        // 获取首页数据
        return view;
    }

    @GetMapping("/pages/{pageName}.html")
    public ModelAndView page(@PathVariable("pageName") String pageName, HttpServletRequest request) {
        String url = "/pages/" + pageName;
        ModelAndView view = new ModelAndView(url);
        return view;
    }

    @GetMapping("/pages/{path}/{pageName}.html")
    public ModelAndView page(@PathVariable("path") String path,
                             @PathVariable("pageName") String pageName, HttpServletRequest request) {
        String url = "/pages/" + path + "/" + pageName;
        ModelAndView view = new ModelAndView(url);
        return view;
    }

    @GetMapping("/pages/{path}/{subPath}/{pageName}.html")
    public ModelAndView page(@PathVariable("path") String path,
                             @PathVariable("subPath") String subPath,
                             @PathVariable("pageName") String pageName, HttpServletRequest request) {
        String url = "/pages/" + path + "/" + subPath + "/" + pageName;
        ModelAndView view = new ModelAndView(url);
        return view;
    }

    @GetMapping("/pages/{path}/{subPath}/{thirdPath}/{pageName}.html")
    public ModelAndView page(@PathVariable("path") String path,
                             @PathVariable("subPath") String subPath,
                             @PathVariable("thirdPath") String thirdPath,
                             @PathVariable("pageName") String pageName, HttpServletRequest request) {
        String url = "/pages/" + path + "/" + subPath + "/" + thirdPath + "/" + pageName;
        ModelAndView view = new ModelAndView(url);
        return view;
    }

}
