package com.lxl.ceshi.jvm;
/**
 * 从JVM调用的角度分析java程序堆内存空间的使用：
 * 当JVM进程启动的时候，会从类加载路径中找到包含main方法的入口类HelloJVM
 * 找到HelloJVM会直接读取该文件中的二进制数据，并且把该类的信息放到运行时的Method内存区域中。
 * 然后会定位到HelloJVM中的main方法的字节码中，并开始执行Main方法中的指令
 * 此时会创建Student实例对象，并且使用student来引用该对象（或者说给该对象命名），其内幕如下：
 * 第一步：JVM会直接到Method区域中去查找Student类的信息，此时发现没有Student类，就通过类加载器加载该Student类文件；
 * 第二步：在JVM的Method区域中加载并找到了Student类之后会在Heap区域中为Student实例对象分配内存，
 * 并且在Student的实例对象中持有指向方法区域中的Student类的引用（内存地址）；
 * 第三步：JVM实例化完成后会在当前线程中为Stack中的reference建立实际的应用关系，此时会赋值给student
 * 接下来就是调用方法
 * 在JVM中方法的调用一定是属于线程的行为，也就是说方法调用本身会发生在线程的方法调用栈：
 * 线程的方法调用栈（Method Stack Frames），每一个方法的调用就是方法调用栈中的一个Frame，
 * 该Frame包含了方法的参数，局部变量，临时数据等 student.sayHello();
 */
public class HelloJVM {
    //在JVM运行的时候会通过反射的方式到Method区域找到入口方法main
    public static void main(String[] args) {//main方法也是放在Method方法区域中的
        /**
         * student(小写的)是放在主线程中的Stack区域中的
         * Student对象实例是放在所有线程共享的Heap区域中的
         */
        Student student = new Student("spark");
        /**
         * 首先会通过student指针（或句柄）（指针就直接指向堆中的对象，句柄表明有一个中间的,student指向句柄，句柄指向对象）
         * 找Student对象，当找到该对象后会通过对象内部指向方法区域中的指针来调用具体的方法去执行任务
         */
        student.sayHello();
    }
}

class Student {
    // name本身作为成员是放在stack区域的但是name指向的String对象是放在Heap中
    private String name;
    public Student(String name) {
        this.name = name;
    }
    //sayHello这个方法是放在方法区中的
    public void sayHello() {
        System.out.println("Hello, this is " + this.name);
    }
}