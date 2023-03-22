package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;

// Bean 创建完成后的处理过程
public interface BeanPostProcessor {

    // 在 Bean 的初始化方法调用之前调用
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    // 在 Bean 的初始化方法调用之后调用
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
