package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定义默认的单例 Bean 注册表，实现 SingletonBeanRegistry 接口，后续可以扩展
 * @Author lijunda
 * @Date 2023/3/17
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	// 保存所有 bean 名称的列表
	protected List<String> beanNames = new ArrayList<>();
	// 保存所有 bean 实例的 Map
	protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

	@Override
	public void registerSingleton( String beanName, Object singletonObject ){
		synchronized( this.singletons ){
			this.singletons.put( beanName, singletonObject );
			this.beanNames.add( beanName );
		}
	}

	@Override
	public Object getSingleton( String beanName ){

		return this.singletons.get( beanName );
	}

	@Override
	public Boolean containsSingleton( String beanName ){

		return this.singletons.containsKey( beanName );
	}

	@Override
	public String[] getSingletonNames(){

		return ( String[] )this.beanNames.toArray();
	}

	protected void removeSingleton( String beanName ){
		synchronized( this.singletons ){
			this.singletons.remove( beanName );
			this.beanNames.remove( beanName );
		}
	}
}
