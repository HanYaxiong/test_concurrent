package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock02 {
    Lock lock = new ReentrantLock();        // 手工锁，必须手动释放锁， 如果有异常，通常在finally里释放锁

    void m1() {
        for (int i = 0; i < 10; i++) {

            try {
                lock.lock();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试获取锁，不管锁定与否都会继续执行
     * 可根据返回值判断是否锁定，还可做相关业务处理
     * 还可以指定tryLock的时间
     */
    void m2() {
        boolean locked = lock.tryLock();        //
        System.out.println("m2");
        if(locked) lock.unlock();
    }
}
