package com.lxl.ceshi.jvm;

/**
 * Date: 2020-06-05-11-05
 * Module:
 * Description:
 *  -Xms30m -Xmx30m -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 *  -Xss1m //栈的大小
 *   -Xms30m -Xmx30m -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 * @author:
 */
public class JVMObject {
    public final static String MAN_TYPE = "man"; // 常量
    public  static String WOMAN_TYPE = "woman"; //静态变量
    static Teacher ts1 = new Teacher();
    Teacher ts2 = new Teacher();

    public static void main(String[] args) throws Exception {
        Teacher t1 = new Teacher();
        t1.setName("Mark");
        t1.setSexType(MAN_TYPE);
        t1.setAge(36);
        for( int i = 0; i < 15; i++){
            System.gc();
        }
        Teacher t2 = new Teacher();
        t2.setName("King");
        t2.setSexType(WOMAN_TYPE);
        t2.setAge(18);
        Thread.sleep(Integer.MAX_VALUE);

    }
}

class Teacher{
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}