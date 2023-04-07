package org.mini.spring.beans.factory.xml;

import org.dom4j.Element;
import org.mini.spring.beans.*;
import org.mini.spring.beans.factory.config.ConstructorArgumentValue;
import org.mini.spring.beans.factory.config.ConstructorArgumentValues;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.support.AbstractBeanFactory;
import org.mini.spring.beans.factory.support.SimpleBeanFactory;
import org.mini.spring.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 将解析好的 xml 文件转换成 BeanDefinition
 * @Author lijunda
 * @Date 2023/3/16
 */
public class XmlBeanDefinitionReader{

	AbstractBeanFactory simpleBeanFactory;

	public XmlBeanDefinitionReader( AbstractBeanFactory simpleBeanFactory ) {
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
			List<String> refs = new ArrayList<>();
			for( Element propertyElement : propertyElements ){
				String type = propertyElement.attributeValue( "type" );
				String name = propertyElement.attributeValue( "name" );
				String value = propertyElement.attributeValue( "value" );
				String ref = propertyElement.attributeValue( "ref" );
				String pv = "";
				boolean isRef = false;
				if( value != null && !value.equals( "" ) ) {
					isRef = false;
					pv = value;
				}else if( ref != null && !ref.equals( "" ) ){
					isRef = true;
					pv = ref;
					refs.add( ref );
				}
				PropertyValue propertyValue = new PropertyValue( type, name, pv, isRef );
				propertyValues.addPropertyValue( propertyValue );
			}
			beanDefinition.setPropertyValues( propertyValues );

			List<Element> contructorElements  = element.elements( "constructor-arg" );
			ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
			for( Element contructorElement : contructorElements ){
				String type = contructorElement.attributeValue( "type" );
				String name = contructorElement.attributeValue( "name" );
				String value = contructorElement.attributeValue( "value" );
				ConstructorArgumentValue argumentValue = new ConstructorArgumentValue( type, name, value );
				argumentValues.addArgumentValue( argumentValue );
			}
			beanDefinition.setPropertyValues( propertyValues );
			String[] refArrau = refs.toArray( new String[0] );
			beanDefinition.setDependsOn( refArrau );

			this.simpleBeanFactory.registerBeanDefinition( beanID, beanDefinition );
		}
	}

}
