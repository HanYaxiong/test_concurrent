package com.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class T05_ArrayBlockingQueue {
    static BlockingQueue<String> queue = new ArrayBlockingQueue(10);        // 有界队列

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.put("a" + i);
        }

        queue.put("a");         // 阻塞式方法，会一直等待队列空出位置
        // queue.add("aa");         // 队列已满的情况下直接报错
        // queue.offer("aa");       // 返回一个成功与否的boolean值
        // queue.offer("aa", 1, TimeUnit.SECONDS);      // 按时间段阻塞，规定时间内等待

        System.out.println(queue);
    }
}
