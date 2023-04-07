package org.mini.spring.beans.factory.annotation;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import org.mini.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        // 通过反射的方式，对每一个属性进行判断，如果有带有 @Autowired 注解的属性，就进行注入
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null){
            for( Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired){
                    String filedName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(filedName);
                    field.setAccessible(true);
                    try {
                        field.set(result, autowiredObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
