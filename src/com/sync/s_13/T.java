package com.sync.s_13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * volatile, 使一个变量在多个线程之间可见
 * A B 线程都是用一个变量, java默认会在A中保留一份Copy, 这个时候B线程如果修改了该变量的值, A线程未必知道
 * 使用volatile关键字, 可让所有线程读到变量改变后的值
 * 下面的代码中running存放于堆内存中的T对象内
 * 当线程t1开始执行的时候, 会把running的值从内存中读取到t1的工作区中, 在运行过程中直接使用这个copy, 而不是每次都从堆内存中读取
 * 这样当主线程修改了running的值后, t1感知不到, 所以并不会停止
 * 使用volatile关键字会强制所有线程从内存中读取
 *
 * volatile并不能保证多个线程同时修改running变量时所带来的不一致问题, 也就是说volatile并不能替代synchronized
 */
public class T {

    volatile int count = 0;

    public void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(()-> t.m(), "t"+i));
        }

        threads.forEach((o)-> o.start());

        threads.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}
