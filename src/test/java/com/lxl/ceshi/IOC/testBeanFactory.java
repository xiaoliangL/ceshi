package com.lxl.ceshi.IOC;

import com.lxl.ceshi.IOC.beans.*;
import com.lxl.ceshi.IOC.samples.ABean;
import com.lxl.ceshi.IOC.samples.ABeanFactory;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Date: 2020-06-10-13-58
 * Module:
 * Description:
 * 一个JUnit4的单元测试用例执行顺序为：
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * @author:
 */

public class testBeanFactory {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();

    @Test
    public void testReigst() throws Exception {
        //构造方法
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABean.class);
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);

        bd.setInitMethodName("init");
        bd.setDestroyMethodName("destroy");
        bf.registerBeanDefinition("aBaen",bd);

    }

    @Test
    public void testReigstStaticFactoryMethod() throws Exception {
        //使用工厂方法的静态方法
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABeanFactory.class);
        bd.setFactoryMethodName("getABean");
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);

        bf.registerBeanDefinition("staticAbaen",bd);
    }

    @Test
    public void testReigstFactoryMethod() throws Exception {
        //使用工厂方法的成员方法
        //注册工厂bean

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABeanFactory.class);
        bf.registerBeanDefinition("factory",bd);

        //注册由工厂方法bena来创建的实例bean
        bd = new GenericBeanDefinition();
        bd.setFactoryBeanName("factory");
        bd.setFactoryMethodName("getABean2");
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);
        bd.setPrimary(true);
        bf.registerBeanDefinition("factoryAbean",bd);
    }

    @AfterClass
    public static void testGetBean() throws  Exception{
        //注册完BeanDefinitioin后，执行typeMap生成
        //提前实现单例化
        bf.preInstantiateSingletons();

        System.out.println(" 构造方法方式--------------------");
        for(int i =0; i<3; i++){
            ABean ab =(ABean) bf.getBean("aBaen");
            ab.doSometing();
        }
        System.out.println(" 静态工厂方法方式--------------------");
        for(int i =0; i<3; i++){
            ABean ab =(ABean) bf.getBean("staticAbaen");
            ab.doSometing();
        }
        System.out.println(" 工厂方法方式--------------------");
        for(int i =0; i<3; i++){
            ABean ab =(ABean) bf.getBean("factoryAbean");
            ab.doSometing();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hook shut down");
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
