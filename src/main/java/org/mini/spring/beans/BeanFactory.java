package org.mini.spring.beans;

/**
 * BeanFactory 是对 Bean 进行管理的工厂，接口中包含对 Bean 处理最基本的两个特性：
 *  获取一个 Bean （getBean）
 *  注册一个 BeanDefinition
 * @Author lijunda
 * @Date 2023/3/16
 */
public interface BeanFactory{

	Object getBean( String beanName ) throws BeansException;
	void registerBeanDefinition( BeanDefinition beanDefinition );

}
