package org.mini.spring.context;

import java.util.EventListener;

/**
 * @Author lijunda
 * @Date 2023/4/4
 */
public class ApplicationListener implements EventListener{

	void onApplicationEvent( ApplicationEvent event ){

		System.out.println( event.toString() );
	}

}
