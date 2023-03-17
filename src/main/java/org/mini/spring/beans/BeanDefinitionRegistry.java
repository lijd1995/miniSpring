package org.mini.spring.beans;

/**
 * BeanDefinition 仓库
 * @Author lijunda
 * @Date 2023/3/17
 */
public interface BeanDefinitionRegistry{

	void registerBeanDefinition( String name, BeanDefinition beanDefinition );
	void removeBeanDefinition( String name );
	BeanDefinition getBeanDefinition( String name );
	boolean containsBeanDefinition( String name );

}
