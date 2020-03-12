package com.sync.s_21;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容量的同步容器, 里面有put, get方法, 以及getCount方法
 * 能够支持两个生产者, 和10个消费者的阻塞调用
 * 使用wait/notify/notifyAll来实现
 *
 * 锁池:假设线程A已经拥有了某个对象(注意:不是类)的锁，而其它的线程想要调用这个对象的某个synchronized方法(或者synchronized块)，由于这些线程在进入对象的synchronized方法之前必须先获得该对象的锁的拥有权，但是该对象的锁目前正被线程A拥有，所以这些线程就进入了该对象的锁池中。
 * 等待池:假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁后，进入到了该对象的等待池中
 */
public class MyContainer1 {

    final LinkedList lists = new LinkedList();
    final int MAX = 10;
    int count = 0;

    synchronized void put(Object o) {
        while (lists.size() == MAX) {       // 为什么用while不用if, 因为用if的话, 两个线程都在锁池里面, 线程A获得锁, 往容器中扔了一个对象, 线程二没有判断, 再仍的时候容器就可能会溢出, 所以应当用while再去判断
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(o);
        count ++    ;
        this.notifyAll();      // 为什么不用nitify, 因为notify只会叫醒一个线程, 如果容器已经满了, 再叫醒的是生产者线程的话, 线程相当于死锁了
    }

    synchronized Object get() {
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count --;
        Object o = lists.removeFirst();
        this.notifyAll();
        return o;
    }

    public static void main(String[] args) {
        MyContainer1 c = new MyContainer1();

        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + ":" + c.get());
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
                    c.put(Thread.currentThread().getName() + "-" + j);
                }
            }, "P" + i).start();
        }
    }
}
