package com.sync.s_20;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock用于替代synchronized
 * 本例中用于锁定this, 只有m1执行完毕的时候, m2才执行
 * 这个是复习synchronized的原始语义
 *
 * 使用reentrantlock能够完成同样的功能
 * 需要注意的是, 必须要手动释放锁
 * 使用synchronized遇到异常的话, jvm会释放锁, 使用lock必须手动释放锁, 因此经常在finnal中进行锁的释放
 */
public class ReentrantLock2 {

    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2....");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLock2 t = new ReentrantLock2();
        new Thread(()-> t.m1(), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()-> t.m2(), "t2").start();
    }
}
