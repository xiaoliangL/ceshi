package com.lxl.ceshi.IOC.beans;

/**
 * Date: 2020-06-10-10-24
 * Module:
 * Description:
 *
 * @author:
 */
public interface BeanDefinitionRegistry {
    //注册
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;
    //获取 BeanDefinition
    BeanDefinition getBeanDefinition(String beanName);

    // 判断是否已经有该bean
    boolean containsBeanDefinition(String beanName);

}
