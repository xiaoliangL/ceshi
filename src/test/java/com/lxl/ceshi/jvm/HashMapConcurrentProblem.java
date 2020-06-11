package com.lxl.ceshi.jvm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 2020-06-08-15-16
 * Module:
 * Description:
 *
 * @author:
 */
public class HashMapConcurrentProblem extends Thread {
    private static Map<Integer, Integer> map = new HashMap();
    private static AtomicInteger at = new AtomicInteger(0);

    @Override
    public void run() {
        while(at.get()< 50000000){
            map.put(at.get(),at.get());
            at.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            Thread thread = new HashMapConcurrentProblem();
            thread.start();
        }
    }
}
