package org.mini.spring.context;

import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import org.mini.spring.beans.factory.support.AbstractBeanFactory;
import org.mini.spring.beans.factory.support.SimpleBeanFactory;
import org.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.mini.spring.core.ClassPathXmlResource;
import org.mini.spring.core.Resource;

/**
 * @Author lijunda
 * @Date 2023/3/16
 *
 * @Description ApplicationContext 是提供一个集成的上下文环境，同事对外提供 BeanFactory 接口，
 * 具体的BeanFactory的实现是包在内部的，后面会逐步演变。
 *
 * Spring 内部也是如此的，实际上内部的 beanFactory 是 DefaultListableBeanFactory
 */

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher{

	AutowireCapableBeanFactory beanFactory;

	public ClassPathXmlApplicationContext( String fileName ){
		this( fileName, true );
	}

	public ClassPathXmlApplicationContext( String fileName, boolean isRefresh ){

		Resource resource = new ClassPathXmlResource( fileName );
		AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( beanFactory );
		reader.loadBeanDefinitions( resource );
		this.beanFactory = beanFactory;
		if( isRefresh ) {
			refresh();
		}
	}

	/**
	 * 新的 refresh() 方法，先注册 BeanPostProcessor，这样 BeanFactory 里面就有解释注解的处理器的
	 * 后续再 getBean 的过程中就可以使用了。
	 */
	public void refresh(){

		registerBeanPostProcessors( this.beanFactory );
		onRefresh();
	}

	private void registerBeanPostProcessors( AutowireCapableBeanFactory beanFactory ) {
		beanFactory.addBeanPostProcessor( new AutowiredAnnotationBeanPostProcessor() );
	}

	private void onRefresh() {
		this.beanFactory.refresh();
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
