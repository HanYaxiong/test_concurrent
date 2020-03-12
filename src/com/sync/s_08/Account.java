package com.sync.s_08;

import java.util.concurrent.TimeUnit;

/**
 * 对一个写方法加锁
 * 读方法不加锁
 * 容易产生脏读
 */
public class Account {

    String name;
    double balance;

    public synchronized void setBalance(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.setBalance("zhangsan", 100), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("lisi"));
    }
}
