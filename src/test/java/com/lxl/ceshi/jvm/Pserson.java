package com.lxl.ceshi.jvm;

/**
 * Date: 2020-06-05-09-49
 * Module:
 * Description:
 *
 * @author:
 */
public class Pserson {

    public int worker() throws Exception{
        int x = 1;
        int y = 2;
        int z = (x + y)  * 10;
        return  z;
    }

    public static void main(String[] args) throws Exception {
        Pserson pserson = new Pserson();
        pserson.worker();
    }
}
