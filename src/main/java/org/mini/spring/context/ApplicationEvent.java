package org.mini.spring.context;

import java.util.EventObject;

/**
 * 监听应用的事件
 * @Author lijunda
 * @Date 2023/3/17
 */
public class ApplicationEvent extends EventObject{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source The object on which the Event initially occurred.
	 * @throws IllegalArgumentException if source is null.
	 */
	public ApplicationEvent( Object source ){

		super( source );
	}
}
