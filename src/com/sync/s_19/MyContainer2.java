package com.sync.s_19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器, 提供两个方法, add, size
 * 写两个线程, 线程1添加10个元素到容器中, 线程二监控容器个数,容器中值为5时, 给出提示并结束
 * 和上一个小程序的区别加上了volatile关键字
 */
public class MyContainer2 {

    volatile List lists = new ArrayList<>();

    void add(Object o) {
        lists.add(o);
    }

    int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer2 container1 = new MyContainer2();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                container1.add(new Object());
                System.out.println("add" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(()-> {
            while (true) {
                if (container1.size() == 5) {
                    System.out.println("t2结束");
                    break;
                }
            }
        }, "t2").start();
    }
}
