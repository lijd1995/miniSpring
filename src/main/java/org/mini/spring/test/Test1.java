package org.mini.spring.test;

import org.mini.spring.beans.BeansException;
import org.mini.spring.context.ClassPathXmlApplicationContext;

/**
 * @Author lijunda
 * @Date 2023/3/16
 */
public class Test1{

	public static void main( String[] args ){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext( "beans.xml" );
		AService aservice = null;
		try{
			aservice = ( AService )ctx.getBean( "aservice" );
		}
		catch( BeansException e ){
			e.printStackTrace();
		}
		aservice.sayHello();
	}

}
