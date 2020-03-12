package com.sync.s_21;

import java.beans.IntrospectionException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量的同步容器, 里面有put, get方法, 以及getCount方法
 * 能够支持两个生产者, 和10个消费者的阻塞调用
 * 使用lock, condition来实现
 * 对比两种方式, Condition的实现方式可以更加精确的指定那些线程被唤醒
 */
public class MyContainer2 {

    Lock lock = new ReentrantLock();
    Condition consumer = lock.newCondition();
    Condition producer = lock.newCondition();

    final LinkedList lists = new LinkedList();
    final int MAX = 10;
    int count = 0;

    void put(Object o) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                    producer.await();
                }
            lists.add(o);
            ++count;
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    Object get() {
        Object o = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            --count;
            o = lists.removeFirst();
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return o;
    }

    public static void main(String[] args) {
        MyContainer2 c = new MyContainer2();

        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                for (int j = 0; j < 5; j++) {
                    System.out.println("消费者" + Thread.currentThread().getName() + "使用了:" + c.get());
                }
            }, "C" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(()-> {
                for (int j = 0; j < 25; j++) {
                    c.put("生产者" + Thread.currentThread().getName() + "生产的" + j);
                }
            }, "P" + i).start();
        }
    }
}
