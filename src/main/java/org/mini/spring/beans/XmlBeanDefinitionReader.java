package org.mini.spring.beans;

import org.dom4j.Element;
import org.mini.spring.core.Resource;

import java.util.List;

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

			List<Element> propertyElements = element.elements( "property" );
			PropertyValues propertyValues = new PropertyValues();
			for( Element propertyElement : propertyElements ){
				String type = propertyElement.attributeValue( "type" );
				String name = propertyElement.attributeValue( "name" );
				String value = propertyElement.attributeValue( "value" );
				PropertyValue propertyValue = new PropertyValue( type, name, value );
				propertyValues.addPropertyValue( propertyValue );
			}
			beanDefinition.setPropertyValues( propertyValues );

			List<Element> contructorElements  = element.elements( "constructor-arg" );
			ArgumentValues argumentValues = new ArgumentValues();
			for( Element contructorElement : contructorElements ){
				String type = contructorElement.attributeValue( "type" );
				String name = contructorElement.attributeValue( "name" );
				String value = contructorElement.attributeValue( "value" );
				ArgumentValue argumentValue = new ArgumentValue( type, name, value );
				argumentValues.addArgumentValue( argumentValue );
			}
			beanDefinition.setPropertyValues( propertyValues );

			this.simpleBeanFactory.registerBeanDefinition( beanDefinition );
		}
	}

}
