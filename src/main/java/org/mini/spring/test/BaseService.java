package org.mini.spring.test;

import org.mini.spring.beans.factory.annotation.Autowired;

/**
 * @Author lijunda
 * @Date 2023/3/17
 */
public class BaseService{

	@Autowired
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
