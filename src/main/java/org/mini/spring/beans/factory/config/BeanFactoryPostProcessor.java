package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;

/**
 * @Author lijunda
 * @Date 2023/4/6
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}