package org.mini.spring.test.event;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义事件源,事件源是触发事件的源头
 */
public class EventSourceDemo {


    private LinkedBlockingQueue<EventListenerDemo> queue = new LinkedBlockingQueue<>();

    public EventSourceDemo() {
        super();
    }

    public void addMyEventListener(EventListenerDemo eventListener) {
        queue.offer(eventListener);
    }

    public void deleteMyEventListener(EventListenerDemo eventListener) {
        queue.remove(eventListener);
    }

    public void notifyMyEvent(EventObjectDemo eventObject) {
        Iterator<EventListenerDemo> iterator = queue.iterator();
        while (iterator.hasNext()) {
            //在类中实例化自定义的监听器对象,并调用监听器方法
            iterator.next().execute(eventObject);
        }
    }


}
