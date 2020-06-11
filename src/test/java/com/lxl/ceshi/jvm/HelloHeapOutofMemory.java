package com.lxl.ceshi.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020-06-08-14-03
 * Module:
 * Description:
 * -verbose:gc -Xms10M -Xmx10M -XX:MaxDirectMemorySize=5M -Xss160k -XX:+PrintGCDetails
 * @author:
 */
class Person{}
public class HelloHeapOutofMemory {
    public static void main(String[] args) {
        System.out.println("HelloHeapOutofMemory");
        List<Person> persons = new ArrayList<>();
        int counter = 0;
        while(true){
            persons.add(new Person());
           // System.out.println("Instance:" + (++counter));
        }
    }
}
