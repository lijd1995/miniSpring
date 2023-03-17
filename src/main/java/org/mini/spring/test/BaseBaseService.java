package org.mini.spring.test;

/**
 * @Author lijunda
 * @Date 2023/3/17
 */
public class BaseBaseService{

	private AServiceImpl as;

	public BaseBaseService(){

	}

	public BaseBaseService( AServiceImpl as ){

		this.as = as;
	}

	public void sayHello(){

		System.out.println("测试 BaseBaseService");
	}

	public AServiceImpl getAs(){

		return as;
	}

	public void setAs( AServiceImpl as ){

		this.as = as;
	}
}
