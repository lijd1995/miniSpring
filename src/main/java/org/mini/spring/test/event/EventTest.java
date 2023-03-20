package org.mini.spring.test.event;

public class EventTest {


    /**
     * 事件源可以注册事件监听器对象，并可以向事件监听器对象发送事件对象，
     * 事件发生后，事件源将事件对象发给已经注册的所有事件监听器，
     * 监听器对象随后会根据事件对象内的相应方法响应这个事件。
     * @param args
     */
    public static void main(String[] args) {
        // 创建事件监听器
        EventListenerDemo eventListener = new EventListenerDemo();
        // 创建事件源
        EventSourceDemo eventSource = new EventSourceDemo();
        // 注册监听器
        eventSource.addMyEventListener(eventListener);
        // 创建事件
        Event event = new Event();
        event.setName("事件");

        EventObjectDemo eventObject = new EventObjectDemo(event);
        // 通知监听器
        eventSource.notifyMyEvent(eventObject);
    }

}
