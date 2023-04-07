package org.mini.spring.context;

import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import org.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import org.mini.spring.beans.factory.support.AbstractBeanFactory;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.beans.factory.support.SimpleBeanFactory;
import org.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.mini.spring.core.ClassPathXmlResource;
import org.mini.spring.core.Resource;
import org.mini.spring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijunda
 * @Date 2023/3/16
 *
 * @Description ApplicationContext 是提供一个集成的上下文环境，同时对外提供 BeanFactory 接口，
 * 具体的BeanFactory的实现是包在内部的，后面会逐步演变。
 *
 * Spring 内部也是如此的，实际上内部的 beanFactory 是 DefaultListableBeanFactory
 *
 *
 */

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

	DefaultListableBeanFactory beanFactory;
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

	public ClassPathXmlApplicationContext( String fileName ){
		this( fileName, true );
	}

	public ClassPathXmlApplicationContext( String fileName, boolean isRefresh ){

		Resource resource = new ClassPathXmlResource( fileName );
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( beanFactory );
		reader.loadBeanDefinitions( resource );
		this.beanFactory = beanFactory;
		if( isRefresh ) {
			try{
				refresh();
			}catch( Exception ex ){

			}
		}
	}

//	/**
//	 * 新的 refresh() 方法，先注册 BeanPostProcessor，这样 BeanFactory 里面就有解释注解的处理器的
//	 * 后续再 getBean 的过程中就可以使用了。
//	 */
//	public void refresh(){
//
//		registerBeanPostProcessors( this.beanFactory );
//		onRefresh();
//	}

	@Override
	public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException{

		return this.beanFactory;
	}

	@Override
	public void setEnviroment( Environment enviroment ){

	}

	@Override
	public Environment getEnviroment(){

		return null;
	}

	private void registerBeanPostProcessors( DefaultListableBeanFactory beanFactory ) {
		beanFactory.addBeanPostProcessor( new AutowiredAnnotationBeanPostProcessor() );
	}

	@Override
	void onRefresh() {
		this.beanFactory.refresh();
	}

	@Override
	void finishRefresh(){
		publishEvent( new ContextRefreshEvent( "Context Refreshed ..." ) );
	}

	@Override
	void registerListeners(){

		ApplicationListener listener = new ApplicationListener();
		this.getApplicationEventPublisher().addApplicationListener( listener );

	}

	@Override
	void initApplicationEventPublisher(){
		ApplicationEventPublisher aep = new SimpleApplicaionEventPublisher();
		this.setApplicationEventPublisher( aep );
	}

	@Override
	void postProcessBeanFactory( ConfigurableBeanFactory beanFactory ){

	}

	@Override
	void registerBeanPostProcessors( ConfigurableBeanFactory beanFactory ){
		this.beanFactory.addBeanPostProcessor( new AutowiredAnnotationBeanPostProcessor() );
	}

	@Override
	public Object getBean( String beanName ) throws BeansException{

		return this.beanFactory.getBean( beanName );
	}

	@Override
	public Boolean containsBean( String beanName ){

		return this.beanFactory.containsBean( beanName );
	}

	@Override
	public void publishEvent( ApplicationEvent event ){
		this.getApplicationEventPublisher().publishEvent( event );
	}

	@Override
	public void addApplicationListener( ApplicationListener listener ){
		this.getApplicationEventPublisher().addApplicationListener( listener );
	}

	@Override
	public boolean isSingleton( String name ){

		return false;
	}

	@Override
	public boolean isPropertype( String name ){

		return false;
	}

	@Override
	public Class<?> getType( String name ){

		return null;
	}

}
