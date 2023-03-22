package org.mini.spring.beans.factory.support;


import org.mini.spring.beans.*;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.config.ConstructorArgumentValue;
import org.mini.spring.beans.factory.config.ConstructorArgumentValues;
import org.mini.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的 Bean 工厂，改造后的 SimpleBeanFactory 对于 Bean 的处理，交给父类 DefaultSingletonBeanRegistry 处理
 * 然后实现了 BeanDefinitionRegistry 接口，那SimpleBeanFactory 既是一个工厂同时也是一个仓库
 * @Author lijunda
 * @Date 2023/3/16
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

	// 保存 BeanDefinitions 的列表
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
	private List<String> beanDefinitionNames = new ArrayList<>();
	private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

	public SimpleBeanFactory(){

	}

	public void refresh(){
		for( String beanName : beanDefinitionNames ){
			try{
				getBean( beanName );
			}
			catch( BeansException e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据 beanName 获取 Bean，改造后交给父类处理，父类处理单例
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	public Object getBean( String beanName ) throws BeansException{

		Object singleton = this.getSingleton( beanName );

		if( singleton == null ){
			// 如果没有实例，先尝试从毛坯实例中获取
			singleton = this.earlySingletonObjects.get( beanName );
			if( singleton == null ){
				BeanDefinition beanDefinition = beanDefinitionMap.get( beanName );
				singleton = createBean( beanDefinition );
				this.registerSingleton( beanName, singleton );

				// 预留beanpostprocessor位置
				// step 1: postProcessBeforeInitialization 初始化前对Bean的处理
				// step 2: afterPropertiesSet
				// step 3: init-method 初始化中对Bean的处理
				// step 4: postProcessAfterInitialization 初始化后对Bean进行处理
			}

		}

		return singleton;
	}

	private Object createBean( BeanDefinition beanDefinition ){
		Class<?> clz = null;
		// 创建毛坯 bean 实例
		Object obj = doCreateBean( beanDefinition );
		// 存放到毛坯实例缓存中
		this.earlySingletonObjects.put( beanDefinition.getId(), obj );

		try{
			clz = Class.forName( beanDefinition.getClassName() );
		}
		catch( ClassNotFoundException e ){
			e.printStackTrace();
		}

		// 补齐 property 的值
		handleProperties( beanDefinition, clz, obj );
		return obj;
	}

	// doCreateBean 创建毛坯实例，仅仅调用构造方法，没有进行属性处理
	private Object doCreateBean( BeanDefinition beanDefinition ) {

		Class<?> clz = null;
		Object obj = null;
		Constructor<?> con = null;

		try{
			clz = Class.forName( beanDefinition.getClassName() );
			// 处理构造器参数
			ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
			// 如果有参数
			if( null != constructorArgumentValues && !constructorArgumentValues.isEmpty() ) {
				Class<?>[] paramTypes = new Class<?>[ constructorArgumentValues.getArgumentCount() ];
				Object[] paramValues = new Object[ constructorArgumentValues.getArgumentCount() ];
				// 对每一个参数，分数据类型分别处理
				for( int i = 0; i < constructorArgumentValues.getArgumentCount(); i++ ){
					ConstructorArgumentValue argumentValue = constructorArgumentValues.getIndexArgumentValue( i );
					if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
						paramTypes[i] = String.class;
						paramValues[i] = argumentValue.getValue();
					}
					else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
						paramTypes[i] = Integer.class;
						paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
					}
					else if ("int".equals(argumentValue.getType())) {
						paramTypes[i] = int.class;
						paramValues[i] = Integer.valueOf((String) argumentValue.getValue()).intValue();
					}
					else {
						paramTypes[i] = String.class;
						paramValues[i] = argumentValue.getValue();
					}
				}
				try{
					// 根据参数的类型选择构造器，然后newInstance的时候将值传入进去
					con = clz.getConstructor( paramTypes );
					obj = con.newInstance( paramValues );
				}catch( Exception ex ){

				}
			} else {
				// 如果没有参数，直接创建实例
				obj = clz.newInstance();
			}
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}

		System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
		return obj;

	}

	private void handleProperties( BeanDefinition beanDefinition, Class<?> clz, Object obj ){

		System.out.println("handle properties for bean : " + beanDefinition.getId());
		PropertyValues propertyValues = beanDefinition.getPropertyValues();
		if( null != propertyValues && !propertyValues.isEmpty() ){
			for( int i = 0; i < propertyValues.size(); i++ ) {
				PropertyValue propertyValue = propertyValues.getPropertyValueList().get( i );
				String type = propertyValue.getType();
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();
				boolean isRef = propertyValue.getIsRef();
				Class<?>[] paramTypes = new Class<?>[ 1 ];
				Object[] paramValues = new Object[ 1 ];

				if( !isRef ) {
					if ("String".equals(type) || "java.lang.String".equals(type)) {
						paramTypes[0] = String.class;
					}
					else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
						paramTypes[0] = Integer.class;
					}
					else if ("int".equals(type)) {
						paramTypes[0] = int.class;
					}
					else {
						paramTypes[0] = String.class;
					}

					paramValues[0] = value;
				}else {
					try{
						paramTypes[0] = Class.forName( type );
						// 调用 getBean 创建 ref 的 bean 实例
						paramValues[0] = getBean( (String)value );
					}
					catch( ClassNotFoundException | BeansException e ){
						e.printStackTrace();
					}

				}

				String methodName = "set" + name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
				Method method = null;
				try{
					method = clz.getMethod( methodName, paramTypes );
					// 获取了构造器，就调用方法，将参数传入进去
					method.invoke( obj, paramValues );
				}
				catch( Exception ex ){
					ex.printStackTrace();
				}
			}
		}
	}




	@Override
	public Boolean containsBean( String beanName ){
		return containsSingleton( beanName );
	}



	/**
	 * 注册 BeanDefinition
	 * @param name
	 * @param beanDefinition
	 */
	@Override
	public void registerBeanDefinition( String name, BeanDefinition beanDefinition ) {

		this.beanDefinitionMap.put( name, beanDefinition );
		this.beanDefinitionNames.add( name );
		if( !beanDefinition.isLazyInit() ){
			try{
				getBean( name );
			}
			catch( BeansException e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移除 BeanDefinition
	 * @param name
	 */
	@Override
	public void removeBeanDefinition( String name ) {
		this.beanDefinitionMap.remove( name );
		this.beanDefinitionNames.remove( name );
		this.removeSingleton( name );
	}

	@Override
	public BeanDefinition getBeanDefinition( String name ){

		return this.beanDefinitionMap.get( name );
	}

	@Override
	public boolean containsBeanDefinition( String name ){

		return this.beanDefinitionMap.containsKey( name );
	}

	public void registerBeanDefinition( BeanDefinition beanDefinition ) {
		this.beanDefinitionMap.put( beanDefinition.getId(), beanDefinition );
	}

	@Override
	public boolean isSingleton( String name ){

		return this.beanDefinitionMap.get( name ).isSingleton();
	}

	@Override
	public boolean isPropertype( String name ){

		return this.beanDefinitionMap.get( name ).isPrototype();
	}

	@Override
	public Class<?> getType( String name ){

		return this.beanDefinitionMap.get( name ).getClass();
	}




}
