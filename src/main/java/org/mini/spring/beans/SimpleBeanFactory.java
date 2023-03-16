package org.mini.spring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的 Bean 工厂，根据 BeanDefinition 注册 Bean 实例
 * @Author lijunda
 * @Date 2023/3/16
 */
public class SimpleBeanFactory implements BeanFactory {

	private List<BeanDefinition> beanDefinitions = new ArrayList<>();
	private List<String> beanNames = new ArrayList<>();
	private Map<String, Object> singletons = new HashMap<>();

	public SimpleBeanFactory(){

	}

	/**
	 * 根据 beanName 获取 Bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object getBean( String beanName ) throws BeansException{

		Object singleton = singletons.get( beanName );

		if( singleton == null ){
			int i = beanNames.indexOf( beanName );
			if( i == -1 ){
				throw new BeansException( "No bean named " + beanName + " is defined" );
			}else {
				// 这块就是按需进行加载
				BeanDefinition beanDefinition = beanDefinitions.get( i );
				try{
					singleton = Class.forName( beanDefinition.getClassName() ).newInstance();
				}
				catch( Exception e ){
					e.printStackTrace();
				}
				// 注册 Bean 实例
				singletons.put( beanDefinition.getId(), singleton );
			}
		}

		return singleton;
	}

	@Override
	public void registerBeanDefinition( BeanDefinition beanDefinition ){
		this.beanDefinitions.add( beanDefinition );
		this.beanNames.add( beanDefinition.getId() );
	}
}
