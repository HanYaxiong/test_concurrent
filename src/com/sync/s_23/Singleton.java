package com.sync.s_23;

import java.util.Arrays;

/**
 * 线程安全的单例模式
 */
public class Singleton {

    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    public static Singleton get() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < 200; i++) {
            ths[i] = new Thread(()-> {
                Singleton.get();
            });
        }

        Arrays.asList(ths).forEach(o -> o.start());
    }
}
