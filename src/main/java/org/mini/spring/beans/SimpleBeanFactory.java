package org.mini.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的 Bean 工厂，改造后的 SimpleBeanFactory 对于 Bean 的处理，交给父类 DefaultSingletonBeanRegistry 处理
 * 然后实现了 BeanDefinitionRegistry 接口，那SimpleBeanFactory 既是一个工厂同时也是一个仓库
 * @Author lijunda
 * @Date 2023/3/16
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

	// 保存 BeanDefinitions 的列表
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
	private List<String> beanDefinitionNames = new ArrayList<>();

	public SimpleBeanFactory(){

	}

	/**
	 * 根据 beanName 获取 Bean，改造后交给父类处理，父类处理单例
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	public Object getBean( String beanName ) throws BeansException{

		Object singleton = this.getSingleton( beanName );

		if( singleton == null ){
			BeanDefinition beanDefinition = beanDefinitionMap.get( beanName );
			if( beanDefinition == null ){
				throw new BeansException( "No bean named " + beanName + " is defined" );
			}

			try{
				singleton = Class.forName( beanDefinition.getClassName() ).newInstance();
				this.registerSingleton( beanName, singleton );
			}
			catch( Exception ex ){
				ex.printStackTrace();
			}
		}

		return singleton;
	}


	@Override
	public Boolean containsBean( String beanName ){
		return containsSingleton( beanName );
	}



	/**
	 * 注册 BeanDefinition
	 * @param name
	 * @param beanDefinition
	 */
	@Override
	public void registerBeanDefinition( String name, BeanDefinition beanDefinition ) {

		this.beanDefinitionMap.put( name, beanDefinition );
		this.beanDefinitionNames.add( name );
		if( !beanDefinition.isLazyInit() ){
			try{
				getBean( name );
			}
			catch( BeansException e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移除 BeanDefinition
	 * @param name
	 */
	@Override
	public void removeBeanDefinition( String name ) {
		this.beanDefinitionMap.remove( name );
		this.beanDefinitionNames.remove( name );
		this.removeSingleton( name );
	}

	@Override
	public BeanDefinition getBeanDefinition( String name ){

		return this.beanDefinitionMap.get( name );
	}

	@Override
	public boolean containsBeanDefinition( String name ){

		return this.beanDefinitionMap.containsKey( name );
	}

	public void registerBeanDefinition( BeanDefinition beanDefinition ) {
		this.beanDefinitionMap.put( beanDefinition.getId(), beanDefinition );
	}

	@Override
	public boolean isSingleton( String name ){

		return this.beanDefinitionMap.get( name ).isSingleton();
	}

	@Override
	public boolean isPropertype( String name ){

		return this.beanDefinitionMap.get( name ).isPrototype();
	}

	@Override
	public Class<?> getType( String name ){

		return this.beanDefinitionMap.get( name ).getClass();
	}




}
