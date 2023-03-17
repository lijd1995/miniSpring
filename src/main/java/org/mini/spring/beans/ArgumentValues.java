package org.mini.spring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对外提供增加、获取、判断等操作方法
 * @Author lijunda
 * @Date 2023/3/17
 */
public class ArgumentValues{

	private final List<ArgumentValue> argumentValueList = new ArrayList<ArgumentValue>();

	public ArgumentValues(){

	}

	public void addArgumentValue( ArgumentValue newValue){
		this.argumentValueList.add(newValue);
	}

	public ArgumentValue getIndexArgumentValue( int index ){
		return this.argumentValueList.get( index );
	}

	public int getArgumentCount(){
		return this.argumentValueList.size();
	}

	public boolean isEmpty(){
		return this.argumentValueList.isEmpty();
	}

}
