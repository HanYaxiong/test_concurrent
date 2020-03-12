package com.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class T04_LinkedBlockingQueue {
    static BlockingQueue<String> queue = new LinkedBlockingQueue();         // 无界队列
    static Random random = new Random();

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                try {
                    queue.put("a" + i);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "p1").start();

        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                for (;;) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " take " + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c"+i).start();
        }
    }
}
