package com.sync.s_11;

import java.util.concurrent.TimeUnit;

/**
 * 程序在执行过程中, 如果发生异常, 默认情况下锁会被释放
 * 所以在并发处理的过程中, 异常应多加小心, 否则可能发生数据不一致的情况
 * 比如在一个web app处理中, 多个servlet线程访问同一资源
 * 如果第一个线程抛出异常, 那其他线程就会进入同步代码区, 有可能访问到异常产生时的据
 * 因此应非常小心的处理同步过程中发生的异常
 */
public class T {

    int count = 0;

    public synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start...");
        while (true) {
            count++;
            try {
                System.out.println(count);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5) {
                int i = 1/0;
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m(), "Thread-1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->t.m(), "Thread-2").start();
    }
}
