package com.sync.s_02;

/**
 * synchronized 关键字
 *
 */
public class T {
    private int count = 10;

    public void m() {
        // 锁定当前对象, 使用this , 避免创建不必要的对象
        synchronized (this) {
            count --;
            System.out.println(Thread.currentThread().getName() + "--count:" + count);
        }
    }

}
