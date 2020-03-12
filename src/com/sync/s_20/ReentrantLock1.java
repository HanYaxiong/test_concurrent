package com.sync.s_20;

import java.util.concurrent.TimeUnit;

/**
 * reentrantlock用于替代synchronized
 * 本例中用于锁定this, 只有m1执行完毕的时候, m2才执行
 * 这个是复习synchronized的原始语义
 */
public class ReentrantLock1 {

    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    synchronized void m2() {
        System.out.println("m2....");
    }

    public static void main(String[] args) {
        ReentrantLock1 t = new ReentrantLock1();
        new Thread(()-> t.m1(), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()-> t.m2(), "t2").start();
    }
}
