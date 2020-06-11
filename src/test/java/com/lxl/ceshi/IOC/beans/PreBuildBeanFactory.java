package com.lxl.ceshi.IOC.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Date: 2020-06-10-18-11
 * Module:
 * Description:
 *
 * @author:
 */
public class PreBuildBeanFactory extends DefaultBeanFactory2 {

    private static final Logger log = LoggerFactory.getLogger(PreBuildBeanFactory.class);

    public void  preInstantiateSingletons() throws Exception{
        for(Map.Entry<String,BeanDefinition> entry : this.beanDefinitionMap.entrySet()){
            String name = entry.getKey();
            BeanDefinition bd = entry.getValue();
            if(bd.isSingleton()){
                this.getBean(name);
                System.out.println("preInstantiate name:" + name);
                if(log.isDebugEnabled()){
                    log.debug("preInstantiate name:" + name);
                }
            }
        }
    }


}
