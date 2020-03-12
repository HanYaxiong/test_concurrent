package com.sync.s_10;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法, 一个对象已经拥有了某个对象的锁, 再次进入时仍会获得该对象的锁
 * 也就是说synchronized获得的锁是可重入的
 * 这个是子类调用父类的同步方法
 */
public class T {

    public synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " m.start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m.end...");
    }

    public static void main(String[] args) {
        new TT().m();
    }

}

class TT extends T {
    @Override
    public synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " child.start...");
        super.m();
        System.out.println(Thread.currentThread().getName() + " child.end...");
    }
}
