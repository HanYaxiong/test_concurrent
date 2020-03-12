package com.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T03_ConcurrentLinkedQueue {
    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();        // 用链表实现的无界队列
        // Deque    ConcurrentLinkedDeque   双端队列

        for (int i = 0; i < 10; i++) {
            queue.offer("a" + i);
        }

        System.out.println(queue);
        System.out.println(queue.size());

        System.out.println(queue.poll());           // 把队列中的第一个值取出来，并删除
        System.out.println(queue.size());

        System.out.println(queue.peek());           // 把队列中第一个值取出来
        System.out.println(queue.size());
    }
}
