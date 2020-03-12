package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock03 {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(()->{
            //lock.lock();
            try {
                lock.lockInterruptibly();
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            // lock.lock();
            try {
                lock.lockInterruptibly();       // 可以被打断的锁,
                System.out.println("t2");
            } catch (InterruptedException e) {
                System.out.println("interrupt");
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();

    }
}
