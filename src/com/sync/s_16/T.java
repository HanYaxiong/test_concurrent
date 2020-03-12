package com.sync.s_16;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized优化
 * 同步代码块中语句越少越好
 */
public class T {

    int count = 0;

    public synchronized void m1() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count ++;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            count ++;
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Long stime = System.currentTimeMillis();
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(()-> t.m2(), "t"+i));
        }

        threads.forEach((o)-> o.start());

        threads.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
        Long etime = System.currentTimeMillis();
        System.out.println(etime - stime);
    }
}
