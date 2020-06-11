package com.lxl.ceshi.jvm;

/**
 * Date: 2020-06-09-09-58
 * Module:
 * Description:
 *
 * @author:
 */
public class UserStack {
    //new 一个栈
    static Stack stack = new Stack();

    public static void main(String[] args) throws InterruptedException {
        for( int i = 0; i<10000; i++ ){
            stack.push(new String[1 * 1000]);
        }
        for( int i = 0; i<10000; i++  ){
            Object o = stack.pop();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

}
