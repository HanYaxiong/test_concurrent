package com.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程池维护了两个队列,
 * queued tasks 任务队列
 * completed tasks 完成队列
 */
public class T02_ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        // 一个线程池，维护者两个队列，任务队列，以及已完成任务队列
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i=0;i<6;i++) {
            service.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        service.shutdown();             // 尝试关闭
        System.out.println(service.isTerminated());     // 任务是否结束了， 未结束，为false
        System.out.println(service.isShutdown());       // 是否关闭线程池了，正在关闭中，为true(关闭不代表已经执行完了)
        System.out.println(service);

        TimeUnit.SECONDS.sleep(5);
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

    }
}
