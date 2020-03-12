package com.sync.s_07;

/**
 * synchronized 关键字
 * 当一个线程调用一个对象的sync hronized方法时, 其他不需要锁的方法可以被执行
 */
public class T {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1.start...");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1.end...");
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " m2.start...");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2.end...");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m1(), "Thread-1").start();
        new Thread(()->t.m2(), "Thread-2").start();

        // 等同于上面的写法
        /*new Thread(t::m1, "Thread-1").start();
        new Thread(t::m2, "Thread-2").start();*/
    }
}
