package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.mini.spring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();


    public void addBeanPostProcessor( AutowiredAnnotationBeanPostProcessor beanPostProcessor ) {
        this.beanPostProcessors.remove( beanPostProcessor );
        this.beanPostProcessors.add( beanPostProcessor );
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {

        Object result = existingBean;

        // 所以这块是可以扩展的，我的 BeanPostProcessor 是可以有多个的
        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {

            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }


        return result;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {

        Object result = existingBean;

        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }

        return null;
    }
}
