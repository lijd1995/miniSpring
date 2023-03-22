package org.mini.spring.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 对外提供增加、获取、判断等操作方法
 * @Author lijunda
 * @Date 2023/3/17
 */
public class ConstructorArgumentValues {

	private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<ConstructorArgumentValue>();

	public ConstructorArgumentValues(){

	}

	public void addArgumentValue( ConstructorArgumentValue newValue){
		this.argumentValueList.add(newValue);
	}

	public ConstructorArgumentValue getIndexArgumentValue(int index ){
		return this.argumentValueList.get( index );
	}

	public int getArgumentCount(){
		return this.argumentValueList.size();
	}

	public boolean isEmpty(){
		return this.argumentValueList.isEmpty();
	}

}
