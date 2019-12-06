package com.benz.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class ApplicationContextUtils {

	private static ApplicationContext context;  
	
	public static void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
	public static Environment getEnvironment(){
		if(context == null) {
			return null;
		}
		return context.getEnvironment();
	}
	
	public static String getActiveProfile() {
		String[] activeProfiles = context.getEnvironment()
				.getActiveProfiles();
		if (activeProfiles != null && activeProfiles.length > 0) {
			return activeProfiles[0];
		}
		return null;
	}
}
