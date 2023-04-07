package org.mini.spring.context;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import org.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import org.mini.spring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 我们只做到从 XML 中读取配置，来获取应用的上下文信息，但实际上 Spring 框架里不只这一种方式。
 * 不论哪种方式，本质都是对应用上下文的处理，所以我们可以抽象出一个 ApplicationContext 的公共部分。
 * @Author lijunda
 * @Date 2023/4/6
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

	private Environment environment;
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
	private long startupDate;
	private final AtomicBoolean active = new AtomicBoolean();
	private final AtomicBoolean closed = new AtomicBoolean();
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Object getBean( String beanName ) throws BeansException{

		return getBeanFactory().getBean( beanName );
	}

	public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors(){
		return this.beanFactoryPostProcessors;
	}


	abstract void onRefresh();
	abstract void finishRefresh();
	abstract void registerListeners();
	abstract void initApplicationEventPublisher();
	abstract void postProcessBeanFactory( ConfigurableBeanFactory beanFactory );
	abstract void registerBeanPostProcessors( ConfigurableBeanFactory beanFactory );



	@Override
	public void refresh() throws BeansException, IllegalStateException{
		// 获取 BeanFactory 进行前置的一些处理操作
		postProcessBeanFactory( getBeanFactory() );
		// 给 BeanFactory 注册 BeanPostProcessor
		registerBeanPostProcessors( getBeanFactory() );
		// 初始化事件发布器
		initApplicationEventPublisher();
		// 进行初始化的流程
		onRefresh();
		// 注册监听器
		registerListeners();
		// 完成上下文初始化流程
		finishRefresh();
	}

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		getBeanFactory().registerSingleton(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return getBeanFactory().getSingleton(beanName);
	}

	@Override
	public Boolean containsSingleton(String beanName) {
		return getBeanFactory().containsSingleton(beanName);
	}

	@Override
	public String[] getSingletonNames() {
		return getBeanFactory().getSingletonNames();
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return getBeanFactory().containsBeanDefinition(beanName);
	}

	@Override
	public int getBeanDefinitionCount() {
		return getBeanFactory().getBeanDefinitionCount();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		return getBeanFactory().getBeanNamesForType(type);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		getBeanFactory().addBeanPostProcessor(beanPostProcessor);

	}

	@Override
	public int getBeanPostProcessorCount() {
		return getBeanFactory().getBeanPostProcessorCount();
	}

	@Override
	public void registerDependentBean(String beanName, String dependentBeanName) {
		getBeanFactory().registerDependentBean(beanName, dependentBeanName);
	}

	@Override
	public String[] getDependentBeans(String beanName) {
		return getBeanFactory().getDependentBeans(beanName);
	}

	@Override
	public String[] getDependenciesForBean(String beanName) {
		return getBeanFactory().getDependenciesForBean(beanName);
	}


	@Override
	public String getApplicationName() {
		return "";
	}
	@Override
	public long getStartupDate() {
		return this.startupDate;
	}
	@Override
	public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Environment getEnvironment() {
		return this.environment;
	}

	@Override
	public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
		this.beanFactoryPostProcessors.add(postProcessor);
	}


	@Override
	public void close() {
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
