package org.mini.spring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个事件发布监听机制可以为后面 ApplicationContext 的使用服务
 * @Author lijunda
 * @Date 2023/4/4
 */
public class SimpleApplicaionEventPublisher implements ApplicationEventPublisher{


	List<ApplicationListener> listeners = new ArrayList<ApplicationListener>();

	@Override
	public void publishEvent( ApplicationEvent event ){

		for( ApplicationListener listener : listeners ){
			listener.onApplicationEvent( event );
		}

	}

	@Override
	public void addApplicationListener( ApplicationListener listener ){
		this.listeners.add( listener );
	}
}
