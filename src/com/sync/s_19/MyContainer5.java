package com.sync.s_19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
 * 下面程序并不是lists = 5时t2结束, 而是t1结束后, t2才接受到通知并退出
 * notify完之后t1释放锁, t2退出之后也必须notify, 通知t1继续执行
 *
 * 使用Latch门闩代替wait, notify来通知
 * 优点:通讯方式简单, 同时可指定等待时间
 * countdownlatch 不涉及锁定, 当latch的值为0时当前线程继续执行
 * 当不涉及同步, 只涉及线程通信的时候用synchronized + wait, notify就显得太重了
 * 这时候就应该考虑 countdownLatch/cyclicbarrier/semaphore
 */
public class MyContainer5 {

    List lists = new ArrayList<>();

    void add(Object o) {
        lists.add(o);
    }

    int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer5 c = new MyContainer5();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(()-> {
            System.out.println("t2 启动");
                while (true) {
                    if (c.size() != 5) {
                        try {
                            latch.await();
                            // latch.await(10000, TimeUnit.SECONDS);        可指定等待时间
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t2结束");
                    break;
                }
        }, "t2").start();

        new Thread(()-> {
            System.out.println("t1启动");
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add" + i);
                    if (c.lists.size() == 5) {
                        // 打开门闩, 让t2得以执行
                        latch.countDown();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }, "t1").start();


    }
}
