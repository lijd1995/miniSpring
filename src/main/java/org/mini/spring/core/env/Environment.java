package org.mini.spring.core.env;

/**
 * 继承 PropertyResolver 用于获取属性
 */
public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);

}
