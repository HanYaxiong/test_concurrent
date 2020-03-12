package com.sync.s_05;

/**
 * synchronized 关键字
 */
public class T implements Runnable{
    private static int count = 10;

    @Override
    public void run() {
        count --;
        System.out.println(Thread.currentThread().getName() + "--count:" + count);
    }

    public static void main(String[] args) {
        T t = new T();
        for (int i = 0; i < 10; i++) {
            new Thread(t, "Thread" + i).start();
        }
    }
}
