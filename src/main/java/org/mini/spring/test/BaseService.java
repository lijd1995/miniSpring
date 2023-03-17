package org.mini.spring.test;

/**
 * @Author lijunda
 * @Date 2023/3/17
 */
public class BaseService{

	private BaseBaseService bbs;

	public void sayHello(){

		System.out.println("base service say hello");
		bbs.sayHello();
	}

	public BaseBaseService getBbs(){

		return bbs;
	}

	public void setBbs( BaseBaseService bbs ){

		this.bbs = bbs;
	}
}
