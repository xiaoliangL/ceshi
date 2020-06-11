package com.lxl.ceshi.IOC.beans;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

/**
 * Date: 2020-06-10-10-27
 * Module:
 * Description:
 *
 * @author:
 */
//Closeable 关闭
public class DefaultBeanFactory0 implements BeanFactory,BeanDefinitionRegistry , Closeable {


    public Object getBean(String beanName) {

        return null;
    }

    public void close() throws IOException {

    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        //1.存储问题 beanDefinition？
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

}
