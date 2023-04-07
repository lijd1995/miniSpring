package org.mini.spring.test;

/**
 * @Author lijunda
 * @Date 2023/3/17
 */
public class BaseBaseService{

	private AService1Impl as;

	public BaseBaseService(){

	}

	public BaseBaseService( AService1Impl as ){

		this.as = as;
	}

	public void sayHello(){

		System.out.println("测试 BaseBaseService");
	}

	public AService1Impl getAs(){

		return as;
	}

	public void setAs( AService1Impl as ){

		this.as = as;
	}
}
