package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock01 {
    Lock lock = new ReentrantLock();        // 手工锁，必须手动释放锁， 如果有异常，通常在finally里释放锁

    void m1() {
        for (int i = 0; i < 10; i++) {

            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLock01 r1 = new ReentrantLock01();
        new Thread(r1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
