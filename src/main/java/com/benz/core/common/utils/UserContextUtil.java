package com.benz.core.common.utils;

import com.benz.core.common.utils.entities.LoginUser;

/**
 * 用户信息上下文（当前操作线程下）
 * 
 * @author wuliang
 * @version $Id: UserContextUtil.java, v 0.1 2019年1月21日 下午5:23:38 wuliang Exp $
 */
public class UserContextUtil {

	private static final ThreadLocal<LoginUser> userContext = new ThreadLocal<LoginUser>();

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	public static LoginUser getUserContext() {
		return userContext.get();
	}

	/**
	 * 设置当前登录用户上下文
	 * 
	 * @param loginUser
	 */
	public static void setUserContext(LoginUser loginUser) {
		userContext.set(loginUser);
	}

	/**
	 * 清除当前登录用户上下文
	 */
	public static void removeUserContext() {
		userContext.remove();
	}
}
