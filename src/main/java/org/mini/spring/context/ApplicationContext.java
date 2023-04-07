package org.mini.spring.context;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ListableBeanFactory;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import org.mini.spring.core.env.Environment;
import org.mini.spring.core.env.EnvironmentCapable;

/**
 * ApplicationContent,支持上下文环境和事件发布
 * @Author lijunda
 * @Date 2023/4/6
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher{
	String getApplicationName();
	long getStartupDate();
	ConfigurableBeanFactory getBeanFactory() throws IllegalStateException;
	void setEnviroment( Environment enviroment );
	Environment getEnviroment();
	void addBeanFactoryPostProcessor( BeanFactoryPostProcessor beanFactoryPostProcessor);
	void refresh() throws BeansException, IllegalStateException;
	void close();
	boolean isActive();

}
