package com.lxl.ceshi.IOC.beans;

/**
 * Date: 2020-06-10-10-19
 * Module:
 * Description:
 *
 * @author:
 */
public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
}
