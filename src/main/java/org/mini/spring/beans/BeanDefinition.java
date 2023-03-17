package org.mini.spring.beans;

/**
 * 增加 Bean 的属性
 * @Author lijunda
 * @Date 2023/3/16
 */

public class BeanDefinition{

	String SCOPE_SINGLETON = "singleton"; // 单例模式
	String SCOPE_PROTOTYPE = "prototype"; // 原型模式
	private boolean lazyInit = false; // Bean 加载的时候是否要初始化
	private String[] dependsOn; // 记录 Bean 之间的依赖关系
	private ArgumentValues constructorArgumentValues;
	private PropertyValues propertyValues;
	private String initMethodName; // 初始化的声明方法
	private volatile Object beanClass;
	private String id;
	private String className;
	private String scope = SCOPE_SINGLETON;

	public BeanDefinition( String id, String className ){

		this.id = id;
		this.className = className;
	}

	public String getId(){

		return id;
	}

	public void setId( String id ){

		this.id = id;
	}

	public String getClassName(){

		return className;
	}

	public void setClassName( String className ){

		this.className = className;
	}

	public boolean isLazyInit(){

		return lazyInit;
	}

	public void setLazyInit( boolean lazyInit ){

		this.lazyInit = lazyInit;
	}

	public String[] getDependsOn(){

		return dependsOn;
	}

	public void setDependsOn( String[] dependsOn ){

		this.dependsOn = dependsOn;
	}

	public ArgumentValues getConstructorArgumentValues(){

		return constructorArgumentValues;
	}

	public void setConstructorArgumentValues( ArgumentValues constructorArgumentValues ){

		this.constructorArgumentValues = constructorArgumentValues;
	}

	public PropertyValues getPropertyValues(){

		return propertyValues;
	}

	public void setPropertyValues( PropertyValues propertyValues ){

		this.propertyValues = propertyValues;
	}

	public String getInitMethodName(){

		return initMethodName;
	}

	public void setInitMethodName( String initMethodName ){

		this.initMethodName = initMethodName;
	}

	public Object getBeanClass(){

		return beanClass;
	}

	public void setBeanClass( Object beanClass ){

		this.beanClass = beanClass;
	}

	public String getScope(){

		return scope;
	}

	public void setScope( String scope ){

		this.scope = scope;
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(scope);
	}

}
