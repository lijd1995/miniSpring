package org.mini.spring.beans;

/**
 * @Author lijunda
 * @Date 2023/3/17
 */
public class PropertyValue{

	private String type;
	private String name;
	private Object value;
	private boolean isRef;

	public PropertyValue( String name, Object value ){

		this.name = name;
		this.value = value;
	}

	public PropertyValue(  String type, String name, Object value, boolean isRef ){

		this.value = value;
		this.type = type;
		this.name = name;
		this.isRef = isRef;
	}

	public Object getValue(){

		return value;
	}

	public String getType(){

		return type;
	}


	public String getName(){

		return name;
	}

	public boolean getIsRef(){

		return isRef;
	}
}
