package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring IoC 引擎
 */
public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory implements ConfigurableListBeanFactory {

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[])this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {

        List<String> result = new ArrayList<>();
        for ( String beanName : this.beanDefinitionNames ){
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class<?> classToMatch = mbd.getClass();
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            }
            else {
                matchFound = false;
            }
            if ( matchFound ){
                result.add(beanName);
            }
        }

        return (String[]) result.toArray();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {

        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }

        return result;
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }
}
