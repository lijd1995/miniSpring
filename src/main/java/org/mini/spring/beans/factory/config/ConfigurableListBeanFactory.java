package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.factory.ListableBeanFactory;

/**
 * 用一个 ConfigurableListBeanFactory 接口把 AutowireCapableBeanFactory、ListableBeanFactory 和 ConfigurableBeanFactory 合并在一起
 *
 * 这几个接口给 BeanFactory 和 BeanDefinition 新增了众多处理方法，用来增强各种特性
 *
 * 这样我们通过接口把这些特性或者能力抽取出来，各自独立互不干扰，通过一个具体的类，来实现这些接口
 *
 * 这样的特性叫做接口隔离原则
 *
 */
public interface ConfigurableListBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

}
