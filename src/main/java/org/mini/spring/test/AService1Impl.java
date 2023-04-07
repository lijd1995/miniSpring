package org.mini.spring.test;

/**
 * @Author lijunda
 * @Date 2023/3/16
 */
public class AService1Impl implements AService1{

	private String name;
	private int level;
	private String property1;
	private String property2;
	private BaseService ref1;

	public AService1Impl(){

	}

	public AService1Impl( String name, int level ){

		this.name = name;
		this.level = level;
		System.out.println(this.name + "," + this.level);
	}

	@Override
	public void sayHello(){
		System.out.println(this.property1 + "," + this.property2);
		ref1.sayHello();
	}



	public String getProperty1(){

		return property1;
	}

	public void setProperty1( String property1 ){

		this.property1 = property1;
	}

	public String getProperty2(){

		return property2;
	}

	public void setProperty2( String property2 ){

		this.property2 = property2;
	}

	public BaseService getRef1(){

		return ref1;
	}

	public void setRef1( BaseService ref1 ){

		this.ref1 = ref1;
	}
}
