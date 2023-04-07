package org.mini.spring.context;

/**
 * 发布事件
 * @Author lijunda
 * @Date 2023/3/17
 */
public interface ApplicationEventPublisher{

	void publishEvent( ApplicationEvent event );
	void addApplicationListener( ApplicationListener listener );

}
