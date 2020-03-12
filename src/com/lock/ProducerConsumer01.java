package com.lock;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerConsumer01 {
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();

    void put(String str) {
        queue.offer(str);
    }

    void get() {
        System.out.println(queue.poll());
    }

    public static void main(String[] args) {
        ProducerConsumer01 c = new ProducerConsumer01();
        for(int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName()  + " put " + j);
                }
            }, "Thread" + i).start();
        }

        for(int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    c.get();
                }
            }).start();
        }
    }
}
