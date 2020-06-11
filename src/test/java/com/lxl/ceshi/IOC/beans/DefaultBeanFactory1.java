package com.lxl.ceshi.IOC.beans;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 2020-06-10-10-27
 * Module:
 * Description:
 *
 * @author:
 */
//Closeable 关闭
public class DefaultBeanFactory1 implements BeanFactory,BeanDefinitionRegistry , Closeable {

    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory1.class);

    //用map来存储bean
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);



    public Object getBean(String beanName) throws Exception {

        return this.doGetBean(beanName);
    }

    private Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName,"beanName 不可为空");
        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd,"BeanDefinition 不能为空");
        Object instance = doCreateInstance(bd);
        return instance;
    }

    private Object doCreateInstance(BeanDefinition bd) throws Exception {
        //拿到类
        Class type = bd.getBeanClass();
        Object instance = null;
        if( type != null){
            if(StringUtils.isBlank(bd.getFactoryMethodName())){
                //构造方法来构造对象
                instance = this.createInstanceByConstructor(bd);
            }else{
                //静态工厂方法
                instance = this.createInstanceByStaticFactoryMethod(bd);
            }
        }else{
            //工厂bean方式来构造对象
            instance = this.createInstancrByFactoryBean(bd);
        }
        //执行初始化方法
        this.doInit(bd,instance);
        return instance;
    }

    //执行初始化方法
    private void doInit(BeanDefinition bd, Object instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(StringUtils.isNotBlank(bd.getInitMethodName())){
            Method method = instance.getClass().getMethod(bd.getInitMethodName(), null);
            method.invoke(instance,null);
        }
    }

    //工厂bean来构造对象
    private Object createInstancrByFactoryBean(BeanDefinition bd) throws Exception {
        //获取工厂bean实例
        Object factoryBean = this.doGetBean(bd.getFactoryBeanName());
        Method method = factoryBean.getClass().getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(factoryBean,null);
    }

    //静态工厂方法构造对象
    private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class type = bd.getBeanClass();
        Method method = type.getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(type,null);
    }

    //构造方法来构造对象
    private Object createInstanceByConstructor(BeanDefinition bd) {
        try {
            return bd.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            log.error("创建bean实例异常，使用方式为构造方法来构造对象，方法： createInstanceByConstructor" );
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() throws IOException {

    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
        Objects.requireNonNull(beanName,"注册bena需要beanName");
        Objects.requireNonNull(beanDefinition,"注册bena需要beanDefinition");
        //校验bean是否合法
        if(!beanDefinition.vaildate()){
            log.error("beanDefinition.vaildate 不通过");
            throw new Exception();
        }
        //判断是否已经包含了beanBame
        if(this.containsBeanDefinition(beanName)){
            log.error("beanName重名");
            throw new Exception();
        }
        this.beanDefinitionMap.put(beanName,beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }


}
