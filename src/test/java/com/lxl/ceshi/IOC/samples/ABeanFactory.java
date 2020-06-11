package com.lxl.ceshi.IOC.samples;

/**
 * Date: 2020-06-10-15-28
 * Module:
 * Description:
 *
 * @author:
 */
public class ABeanFactory {

    //静态工厂方法
    public static ABean getABean(){
        return new ABean();
    }

    //成员工厂方法
    public ABean getABean2(){
        return new ABean();
    }
}
