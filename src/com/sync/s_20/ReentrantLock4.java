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
 */
public class ReentrantLock4 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(()-> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        Thread t2 = new Thread(()-> {
            try {
                // lock.lock();
                lock.lockInterruptibly();       // 可以对interrupt方法作出响应
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interruptibly");
                e.printStackTrace();
            }
        }, "t2");
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();

    }
}
