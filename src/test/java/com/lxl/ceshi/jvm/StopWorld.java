package com.lxl.ceshi.jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * Date: 2020-06-08-14-37
 * Module:
 * Description:
 * -XX:+PrintGCDetails
 * @author:
 */
public class StopWorld {
    public static class FillListThread extends Thread{
        List<byte[]> list = new LinkedList<>();

        @Override
        public void run() {
            try{
                while (true) {
                    if(list.size() * 512 / 1024 / 1024 >= 999){
                        list.clear();
                        System.out.println("list is clear");
                    }
                    byte[] bl;
                    for(int i = 0; i < 100; i++){
                        bl = new byte[512];
                        list.add(bl);
                    }
                    Thread.sleep(100);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static class TimeThread extends Thread{
        public final static long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            try{
                while (true) {
                     long t = System.currentTimeMillis() - startTime;
                    System.out.println( t / 1000 + "." + t % 1000);
                    Thread.sleep(100);//0.1s
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FillListThread thread1 = new FillListThread();
        thread1.setName("添加");
        TimeThread thread2 = new  TimeThread();
        thread2.setName("打印时间");
        thread1.start();
        thread2.start();

    }
}
