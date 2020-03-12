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
 *
 * 使用reentrantlock还可以调用 lockInterruptibly 方法, 可以对线程interrupt方法做出响应,
 * 下面的小程序表示: 一个线程长期占有一把锁, 另一个线程也在等这把锁, 如果使用lockInterruptibly, 就表示别的线程可以打断它, 告诉它不用等待了, 但是用lock 是不能被其他线程打断的
 *
 * reentrantlock 还可以设置为公平锁
 * 公平锁的意思就是哪个线程等的时间长, 哪个线程获得锁
 * 非公平锁效率高于公平锁, 因为不用计算时间
 *
 * 下面的小例子程序表示,使用公平锁后两个线程交叉执行, 而不使用的话,则是随机的
 */
public class ReentrantLock5 extends Thread {

    static Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock5 r = new ReentrantLock5();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
}
