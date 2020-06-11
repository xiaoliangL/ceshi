package com.lxl.ceshi.jvm;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Date: 2020-06-09-10-42
 * Module:
 * Description:
 * 线程死锁
 * @author:
 */
public class NormalDeadLock {
    private static Object LockA = new Object();
    private static Object LockB = new Object();

    Lock lock = new ReentrantLock();

    //先拿第一个锁，再拿第二个锁
    private static void firstToSecond() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (LockA){
            System.out.println(threadName + " get A");
            Thread.sleep(100);
            synchronized (LockB){
                System.out.println(threadName + "get B");
            }
        }
    }

    //先拿第二个锁，再拿第一个锁
    private static void secondToFirst() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (LockA){
            System.out.println(threadName + " get A");
            Thread.sleep(100);
            synchronized (LockB){
                System.out.println(threadName + "get B");
            }
        }
    }
    private static class TestThread extends Thread{
        private String name;
        public TestThread(String name){
            this.name = name;
        }

        @Override
        public void run() {
           Thread.currentThread().setName(name);
            try {
                secondToFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main 线程");
        TestThread testThread = new TestThread("TestThread 2");
        testThread.start();
        try {
            firstToSecond();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
