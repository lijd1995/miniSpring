package org.mini.spring.beans.factory;

import org.mini.spring.beans.BeansException;

/**
 * BeanFactory 是对 Bean 进行管理的工厂，接口中包含对 Bean 处理最基本的两个特性：
 *  获取一个 Bean （getBean）
 *  注册一个 BeanDefinition
 * @Author lijunda
 * @Date 2023/3/16
 */
public interface BeanFactory{

	Object getBean( String name ) throws BeansException;
	Boolean containsBean( String name );
	boolean isSingleton( String name );
	boolean isPropertype( String name );
	Class<?> getType( String name );

}
