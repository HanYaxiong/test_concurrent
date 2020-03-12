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
 *
 * 使用reentrantlock, 可以进行"尝试锁定" trylock, 这样无法锁定, 或者在指定时间内无法锁定, 线程可以决定是否等待
 */
public class ReentrantLock3 {

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

    /**
     * 使用trylock进行尝试锁定, 不管锁定与否,方法都将继续运行
     * 根据返回值来判断是否锁定
     * 可以指定trylock的时间
     */
    void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock3 t = new ReentrantLock3();
        new Thread(()-> t.m1(), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()-> t.m2(), "t2").start();
    }
}
