package com.lxl.ceshi.IOC.samples;

/**
 * Date: 2020-06-10-15-25
 * Module:
 * Description:
 *
 * @author:
 */
//使用构造方法
public class ABean {

    public void doSometing(){
        System.out.println(System.currentTimeMillis() + " " + this);
    }

    public void init(){
        System.out.println(  "ABean.init()被执行了 " );
    }
    public void destroy(){
        System.out.println(  "ABean.destroy()被执行了 " );
    }
}
