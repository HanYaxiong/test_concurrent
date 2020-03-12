package com.sync.s_22;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 线程局部变量
 * ThreadLocal 是空间换时间, synchronized是时间换空间
 *
 */
public class ThreadLocal2 {

    static ThreadLocal tl = new ThreadLocal();

    public static void main(String[] args) {
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}
