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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 2020-06-10-10-27
 * Module:
 * Description:
 *
 * @author:
 */
//添加单例
public class DefaultBeanFactory2 implements BeanFactory,BeanDefinitionRegistry , Closeable {

    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory2.class);

    //用map来存储bean
    public Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);
    //单例map
    public Map<String,Object> singletonBeanMap = new ConcurrentHashMap<String,Object>(256);

    public Object getBean(String beanName) throws Exception {

        return this.doGetBean1(beanName);
    }

    private Object doGetBean(String beanName) throws Exception {

        Objects.requireNonNull(beanName,"beanName 不可为空");

        Object instance = singletonBeanMap.get(beanName);
        if(instance != null){
            return instance;
        }

        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd,"BeanDefinition 不能为空");

        if(bd.isSingleton()){//如果是单例
            instance = doCreateInstance(bd);
            this.singletonBeanMap.put(beanName,instance);
        }else{
            instance = doCreateInstance(bd);
        }

        return instance;
    }

    //单例双重检查 + 锁实现
    private Object doGetBean1(String beanName) throws Exception{
        Objects.requireNonNull(beanName,"beanName 不可为空");
        //单例怎么实现，如何存储，保证单例？
        //第一次检查
        Object instance = singletonBeanMap.get(beanName);
        if(instance != null){
            return instance;
        }

        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd,"BeanDefinition 不能为空");
        if(bd.isSingleton()){//如果是单例  简单方式参考：doGetBean2
            synchronized (this.singletonBeanMap){//枷锁
                instance = this.singletonBeanMap.get(beanName);
                if( instance == null){ //第二次检查
                    instance = doCreateInstance(bd);
                    this.singletonBeanMap.put(beanName,instance);
                }
            }
        }else{
            instance = doCreateInstance(bd);
        }
        return instance;
    }

    //单例双重检查 + 锁实现
    private Object doGetBean2(String beanName) throws Exception{
        Objects.requireNonNull(beanName,"beanName 不可为空");
        //单例怎么实现，如何存储，保证单例？
        //第一次检查
        Object instance = singletonBeanMap.get(beanName);
        if(instance != null){
            return instance;
        }

        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd,"BeanDefinition 不能为空");
        if(bd.isSingleton()){//如果是单例
            instance = this.singletonBeanMap.computeIfAbsent(beanName,k -> {
               try {
                   return doCreateInstance(bd);
               } catch (Exception e) {
                   e.printStackTrace();
                   return null;
               }
            });
        }else{
            instance = doCreateInstance(bd);
        }
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
        //单例实例的销毁方法
        for(Map.Entry<String, BeanDefinition> e :  this.beanDefinitionMap.entrySet()){
            String beanName = e.getKey();
            BeanDefinition bd = e.getValue();
            //如果是单例并且制定销毁方法
            if(bd.isSingleton() && StringUtils.isNotBlank(bd.getDestroyMethodName())){
                Object instance = this.singletonBeanMap.get(beanName);
                try {
                    Method method = instance.getClass().getMethod(bd.getDestroyMethodName(), null);
                    method.invoke(instance,null);
                } catch (IllegalAccessException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }
        //原型bean 销毁 spring 无法处理
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
