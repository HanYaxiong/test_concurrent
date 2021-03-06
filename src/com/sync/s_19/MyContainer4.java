package com.sync.s_19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器, 提供两个方法, add, size
 * 写两个线程, 线程1添加10个元素到容器中, 线程二监控容器个数,容器中值为5时, 给出提示并结束
 * 和上一个小程序的区别加上了volatile关键字
 *
 * 但是使用while(true)死循环很浪费cpu
 * 下面使用wait(), notify()来实现, wait()会释放锁, notify() 不会释放锁
 * 需要注意的是, 运用这种方法必须要保证t2先执行, 也就是首先让t2先先监听才可以
 *
 * 下面程序并不是lists = 5时t2结束, 而是t1结束后, t2才接受到通知并退出
 * notify完之后t1释放锁, t2退出之后也必须notify, 通知t1继续执行
 */
public class MyContainer4 {

    List lists = new ArrayList<>();

    void add(Object o) {
        lists.add(o);
    }

    int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer4 c = new MyContainer4();

        final Object lock = new Object();

        new Thread(()-> {
            System.out.println("t2 启动");
            synchronized (lock) {   // 必须先锁定lock对象, 再去调用wait方法
                while (true) {
                    if (c.size() != 5) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t2结束");
                    break;
                }
                lock.notify();
            }
        }, "t2").start();

        new Thread(()-> {
            System.out.println("t1启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add" + i);
                    if (c.lists.size() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();


    }
}
