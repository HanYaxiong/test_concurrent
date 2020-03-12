package com.sync.s_04;

/**
 * synchronized 关键字
 *
 */
public class T {
    private static int count = 10;

    public synchronized static void m() {  // 锁定的是T.class这个对象(是Class类的一个对象), 等同于synchronized(T.class)
        count --;
        System.out.println(Thread.currentThread().getName() + "--count:" + count);
    }

    public static void mm() {
        synchronized (T.class) {
            count--;
        }
    }


}
