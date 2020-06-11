package com.lxl.ceshi.jvm;

/**
 * Date: 2020-06-09-09-55
 * Module:
 * Description:
 *  一个栈,存在内存泄漏问题
 * @author:
 */
public class Stack {
    public Object[] elements;//数组来保存
    public  int size = 0;
    private static final int Cap = 20000;//容量

    public Stack(){
        elements = new Object[Cap];
    }

    public void push(Object e){
        elements[size] = e;
        size ++;
    }

    public Object pop(){
        size = size -1;
        Object o = elements[size];
        //设置为空，让GC回收，这样写不会出现内存泄漏
        elements[size] = null;
        return  o;
    }
}
