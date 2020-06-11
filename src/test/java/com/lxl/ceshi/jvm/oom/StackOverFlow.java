package com.lxl.ceshi.jvm.oom;

/**
 * Date: 2020-06-08-17-19
 * Module:
 * Description:
 * 栈溢出：-Xss=1m
 * @author:
 */
public class StackOverFlow {
    public void txt(){
        txt();
    }
    public static void main(String[] args) {
        StackOverFlow stackOverFlow = new StackOverFlow();
        stackOverFlow.txt();
    }
}
