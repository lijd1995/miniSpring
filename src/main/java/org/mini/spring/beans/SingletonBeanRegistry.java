package org.mini.spring.beans;

/**
 * 单例 Bean 注册表接口类
 * @Author lijunda
 * @Date 2023/3/17
 */
public interface SingletonBeanRegistry{

	/**
	 * 注册单例 Bean
	 * @param beanName
	 * @param singletonObject
	 */
	void registerSingleton( String beanName, Object singletonObject );

	/**
	 * 获取单例 Bean
	 * @param beanName
	 * @return
	 */
	Object getSingleton( String beanName );

	/**
	 * 判断是否包含单例 Bean
	 * @param beanName
	 * @return
	 */
	Boolean containsSingleton( String beanName );

	/**
	 * 获取所有单例 Bean 的名称
	 * @return
	 */
	String[] getSingletonNames();

}
