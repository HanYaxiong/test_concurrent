package com.sync.s_03;

/**
 * synchronized 关键字
 *
 */
public class T {
    private int count = 10;

    public synchronized void m() {  // 如果一个方法在开始的时候就获得锁, 结束的时候释放, 这个时候可以把synchronized放到方法上, 等同于s_02.T.synchronized(this)
        count --;
        System.out.println(Thread.currentThread().getName() + "--count:" + count);
    }

}
