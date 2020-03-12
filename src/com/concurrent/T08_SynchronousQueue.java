package com.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 一种容量为0 的特殊的TransferQueue
 */
public class T08_SynchronousQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        queue.put("aaa");       // 容量为0， 必须有一个消费者在这里等着
    }

}
