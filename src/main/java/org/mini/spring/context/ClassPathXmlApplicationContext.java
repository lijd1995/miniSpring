package org.mini.spring.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mini.spring.beans.BeanDefinition;
import org.mini.spring.beans.BeanFactory;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.SimpleBeanFactory;
import org.mini.spring.beans.XmlBeanDefinitionReader;
import org.mini.spring.core.ClassPathXmlResource;
import org.mini.spring.core.Resource;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lijunda
 * @Date 2023/3/16
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
