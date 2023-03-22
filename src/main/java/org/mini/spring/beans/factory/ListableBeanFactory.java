package org.mini.spring.beans.factory;

import org.mini.spring.beans.BeansException;

import java.util.Map;

/**
 * 对bean工厂的扩展，提供一些Bean的特性
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 是否包含指定名称的bean定义
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取bean定义的数量
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 获取所有bean definition的名称
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取指定类型的所有bean名称
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 获取指定类型的所有bean
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
