package com.lxl.ceshi.jvm.oom;

/**
 * Date: 2020-06-08-17-15
 * Module:
 * Description:
 * VM AG: -Xms30m -Xmx30m -XX:+PrintGCDetails
 *  堆溢出
 * @  :
 */
public class HeapOom {
    public static void main(String[] args) {
        String[] strings = new String[35 * 1024 * 1024];
    }
}
