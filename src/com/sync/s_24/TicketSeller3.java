package com.sync.s_24;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票, 每张票都有一个编号
 * 同时有十个窗口对外售票
 * 写一个模拟程序
 *
 * 虽然size和remove都是原子性的, 但是两个方法之间的过程会被打断所以还是会有问题
 */
public class TicketSeller3 {

    static Vector<String> tickets = new Vector();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                while (true) {
                    synchronized (tickets) {
                        if (tickets.size() > 0) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("销售了---:" + tickets.remove(0));
                        }
                    }
                }
            }).start();
        }
    }
}
