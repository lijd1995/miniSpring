package org.mini.spring.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * 解析 xml 配置文件
 * @Author lijunda
 * @Date 2023/3/16
 */
public class ClassPathXmlResource implements Resource{

	Document document;
	Element rootElement;
	Iterator<Element> elementIterator;

	public ClassPathXmlResource( String fileName ) {

		SAXReader saxReader = new SAXReader();
		URL xmlPath = this.getClass().getClassLoader().getResource( fileName );
		// 配置文件装载进来，生成一个迭代器，可以用于遍历
		try{
			this.document = saxReader.read( xmlPath );
			this.rootElement = document.getRootElement();
			this.elementIterator = this.rootElement.elementIterator();
		}catch( Exception ex ){
			ex.printStackTrace();
		}
	}

	@Override
	public boolean hasNext(){

		return this.elementIterator.hasNext();
	}

	@Override
	public Object next(){

		return this.elementIterator.next();
	}
}
