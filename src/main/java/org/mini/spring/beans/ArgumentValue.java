package org.mini.spring.beans;

/**
 * <constructor-args> 标签内容
 * @Author lijunda
 * @Date 2023/3/17
 */
public class ArgumentValue{

	private String type;
	private String name;
	private Object value;

	public ArgumentValue( Object value, String type ){

		this.value = value;
		this.type = type;
	}

	public ArgumentValue( Object value, String type, String name ){

		this.value = value;
		this.type = type;
		this.name = name;
	}

	public Object getValue(){

		return value;
	}

	public void setValue( Object value ){

		this.value = value;
	}

	public String getType(){

		return type;
	}

	public void setType( String type ){

		this.type = type;
	}

	public String getName(){

		return name;
	}

	public void setName( String name ){

		this.name = name;
	}
}
