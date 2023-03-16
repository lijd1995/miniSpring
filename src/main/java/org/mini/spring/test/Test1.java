package org.mini.spring.test;

import org.mini.spring.ClassPathXmlApplicationContext;

/**
 * @Author lijunda
 * @Date 2023/3/16
 */
public class Test1{

	public static void main( String[] args ){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext( "beans.xml" );
		AService aservice = (AService )ctx.getBean( "aservice" );
		aservice.sayHello();
	}

}
