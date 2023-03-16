package org.mini.spring.context;

import org.mini.spring.beans.BeanDefinition;
import org.mini.spring.beans.BeanFactory;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.SimpleBeanFactory;
import org.mini.spring.beans.XmlBeanDefinitionReader;
import org.mini.spring.core.ClassPathXmlResource;
import org.mini.spring.core.Resource;

/**
 * @Author lijunda
 * @Date 2023/3/16
 *
 * @Description ApplicationContext 是提供一个集成的上下文环境，同事对外提供 BeanFactory 接口，
 * 具体的BeanFactory的实现是包在内部的，后面会逐步演变。
 *
 * Spring 内部也是如此的，实际上内部的 beanFactory 是 DefaultListableBeanFactory
 */

public class ClassPathXmlApplicationContext implements BeanFactory{

	BeanFactory beanFactory;

	public ClassPathXmlApplicationContext( String fileName ){

		Resource resource = new ClassPathXmlResource( fileName );
		BeanFactory beanFactory = new SimpleBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( beanFactory );
		reader.loadBeanDefinitions( resource );
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getBean( String beanName ) throws BeansException{

		return this.beanFactory.getBean( beanName );
	}

	@Override
	public void registerBeanDefinition( BeanDefinition beanDefinition ){

		this.beanFactory.registerBeanDefinition( beanDefinition );
	}
}
