package org.mini.spring.beans;

import org.dom4j.Element;
import org.mini.spring.core.Resource;


/**
 * 将解析好的 xml 文件转换成 BeanDefinition
 * @Author lijunda
 * @Date 2023/3/16
 */
public class XmlBeanDefinitionReader{

	SimpleBeanFactory simpleBeanFactory;

	public XmlBeanDefinitionReader( SimpleBeanFactory simpleBeanFactory ) {
		this.simpleBeanFactory = simpleBeanFactory;
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
			this.simpleBeanFactory.registerBeanDefinition( beanDefinition );
		}
	}

}
