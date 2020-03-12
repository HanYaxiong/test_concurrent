package com.sync.s_09;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法, 一个对象已经拥有了某个对象的锁, 再次进入时仍会获得该对象的锁
 * 也就是说synchronized获得的锁是可重入的
 */
public class T {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1.start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.m2();
    }

    public synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2.end...");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m1(), "Thread-1").start();
    }
}
