package org.mini.spring.test.event;

import java.util.EventListener;

public class EventListenerDemo implements EventListener {

    public void execute(EventObjectDemo eventObject) {
        System.out.println(eventObject.getSource());
    }
}
