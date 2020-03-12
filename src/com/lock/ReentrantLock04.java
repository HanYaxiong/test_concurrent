package com.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock04 {
    Lock lock = new ReentrantLock(true);        // 指定为公平锁（谁等待的时间长，谁先来），效率低

    void m1(){
        for (int i = 0; i < 100; i++) {
            lock.lock();
            System.out.println(Thread.currentThread().getName());
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock04 r4 = new ReentrantLock04();
        new Thread(r4::m1, "t1").start();
        new Thread(r4::m1, "t2").start();
    }

}
