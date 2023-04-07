package org.mini.spring.context;

/**
 * @Author lijunda
 * @Date 2023/4/4
 */
public class ContextRefreshEvent extends ApplicationEvent{

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param arg0 The object on which the Event initially occurred.
	 * @throws IllegalArgumentException if source is null.
	 */
	public ContextRefreshEvent( Object arg0 ){

		super( arg0 );
	}

	public String toString(){

		return this.msg;
	}
}
