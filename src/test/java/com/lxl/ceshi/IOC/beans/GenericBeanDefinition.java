package com.lxl.ceshi.IOC.beans;

/**
 * Date: 2020-06-10-15-30
 * Module:
 * Description:
 *
 * @author:
 */
public class GenericBeanDefinition implements BeanDefinition {
    private Class<?> beanClass;

    private String scope = BeanDefinition.SCOPE_SINGLETON;

    private String factoryBeanName;

    private String factoryMethodName;

    private String initMethodName;

    private String destroyMethodName;

    private Boolean primary;

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    @Override
    public Class getBeanClass() {
        return this.beanClass;
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public String getInitMethodName() {
        return this.initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }
}
