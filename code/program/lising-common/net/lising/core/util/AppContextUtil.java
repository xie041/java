package net.lising.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;
	}
	public static Object getBean(String serviceName){
		return context.getBean(serviceName);
	}
	
}
