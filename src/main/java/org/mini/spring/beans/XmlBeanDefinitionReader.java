package org.mini.spring.beans;

import org.dom4j.Element;
import org.mini.spring.core.Resource;


/**
 * 将解析好的 xml 文件转换成 BeanDefinition
 * @Author lijunda
 * @Date 2023/3/16
 */
public class XmlBeanDefinitionReader{

	BeanFactory beanFactory;

	public XmlBeanDefinitionReader( BeanFactory beanFactory ) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 将解析好的 xml 文件转换成 BeanDefinition
	 * @param resource
	 */
	public void loadBeanDefinitions( Resource resource ){
		while( resource.hasNext() ){
			Element element = ( Element ) resource.next();
			String beanID = element.attributeValue( "id" );
			String beanClassName = element.attributeValue( "class" );
			BeanDefinition beanDefinition = new BeanDefinition( beanID, beanClassName );
			this.beanFactory.registerBeanDefinition( beanDefinition );
		}
	}

}
