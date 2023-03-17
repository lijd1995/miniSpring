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

	private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<Integer, ArgumentValue>();
	private final List<ArgumentValue> genericArgumentValues = new ArrayList<ArgumentValue>();

	public ArgumentValues(){

	}

	public void addArgumentValue(Integer index, ArgumentValue newValue){
		this.indexedArgumentValues.put(index, newValue);
	}

	public boolean hasIndexedArgumentValues( int index ){
		return this.indexedArgumentValues.containsKey( index );
	}

	public ArgumentValue getIndexArgumentValue( int index ){
		return this.indexedArgumentValues.get( index );
	}

	/**
	 * 添加
	 * @param value
	 * @param type
	 */
	public void addGenericArgumentValue(Object value, String type){
		this.genericArgumentValues.add(new ArgumentValue(value, type));
	}

	/**
	 * 添加，如果存在就移除，对之前的内容进行覆盖
	 * @param newValue
	 */
	private void addGenericArgumentValue(ArgumentValue newValue){

		if( newValue.getName() != null ){
			for( Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext(); ){
				ArgumentValue currentValue = it.next();
				if( newValue.getName().equals( currentValue.getName() ) ){
					it.remove();
				}
			}
		}

		this.genericArgumentValues.add(newValue);
	}

	/**
	 * 根据名称来获取参数内容
	 * @param requiredName
	 * @return
	 */
	public ArgumentValue getGenericArgumentValue( String requiredName ) {
		for( ArgumentValue valueHolder : this.genericArgumentValues ) {

			if( valueHolder.getValue() != null && (requiredName == null || !valueHolder.getName().equals( valueHolder.getName() )) ){
				continue;
			}

			return valueHolder;
		}

		return null;
	}

	public int getArgumentCount(){
		return this.genericArgumentValues.size();
	}

	public boolean isEmpty(){
		return this.genericArgumentValues.isEmpty();
	}

}
