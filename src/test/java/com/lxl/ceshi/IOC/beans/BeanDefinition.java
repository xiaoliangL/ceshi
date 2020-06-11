package com.lxl.ceshi.IOC.beans;

import org.apache.commons.lang3.StringUtils;

public interface BeanDefinition {
    //单例
    String SCOPE_SINGLETON = "singleton";
    //多里
    String SCOPE_PROTOTYPE = "prototype";
    //获取bean的class
    Class<?> getBeanClass();
    //获取工厂方法名
    String getFactoryMethodName();
    //获取工厂Bean的名字
    String getFactoryBeanName();
    //获取作用域
    String getScope();
    //判断是否为单例
    boolean isSingleton();

    boolean isPrototype();
    //获取初始化方法名
    String getInitMethodName();
    //获取销毁的方法名
    String getDestroyMethodName();

    //验证 bean 是否合法
    default Boolean vaildate(){
        //没定义class 工厂bean或者工厂方法没指定，则不合法
        if(this.getBeanClass() == null){
            //成员工厂方法
            if(StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())){
                return false;
            }
        }

        //定义了lei,又定义工厂bean，不合法
        if(this.getBeanClass() != null && StringUtils.isNotBlank(getFactoryBeanName())){
            return false;
        }
        return true;
    }

}
