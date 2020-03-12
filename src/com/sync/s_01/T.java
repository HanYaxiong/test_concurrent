package com.sync.s_01;

/**
 * synchronized 关键字
 * 对某一个对象加锁, 对object指向的对象加锁,
 */
public class T {
    private int count = 10;
    private Object object = new Object();

    public void m() {
        // 任何要执行此段代码的线程, 都必须先得到object对象
        synchronized (object) {
            count --;
            System.out.println(Thread.currentThread().getName() + "--count:" + count);
        }
    }

}
