package com.sync.s_17;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * synchronized优化
 * 同步代码块中语句越少越好
 */
public class T {

    Object o = new Object();

    public void m1() {
        synchronized (o) {
            while (true) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()-> t.m1(), "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.o = new Object();
        new Thread(()-> t.m1(), "t2").start();
    }
}
