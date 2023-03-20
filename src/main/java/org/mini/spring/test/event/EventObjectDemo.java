package org.mini.spring.test.event;

import java.util.EventObject;

/**
 * 自定义事件
 */
public class EventObjectDemo extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventObjectDemo(Object source) {
        super(source);
    }
}
