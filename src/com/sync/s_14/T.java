package com.sync.s_14;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样问题的更高效的方法是使用AtomicXXX类
 * AtomicXXX类本身的方法都是原子性的, 但不能保证多个方法连续调用是原子性的
 */
public class T {

    AtomicInteger count = new AtomicInteger(0);

    public void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(()-> t.m(), "t"+i));
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
    }
}
