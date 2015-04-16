package com.myking520.github;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContext {
	private static SpringContext factory = new SpringContext();

	private static String configLocation = "config" + File.separator + "app*.xml";

	private static ApplicationContext ctx = new FileSystemXmlApplicationContext(configLocation);

	public static SpringContext getInstance() {
		return factory;
	}

	public static <T> T getBean(String beanName) {
		return (T) ctx.getBean(beanName);
	}

	public static void main(String[] args) {
		GameNioSocketAcceptor gameNioSocketAcceptor = SpringContext.getInstance().getBean("gameNioSocketAcceptor");
		gameNioSocketAcceptor.start();
	}
}
