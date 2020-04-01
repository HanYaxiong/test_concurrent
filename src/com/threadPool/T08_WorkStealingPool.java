package com.threadPool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 工作/任务窃取
 * 每个线程都有自己的任务队列, 当当前线程维护的任务队列空了, 他会从其他线程的任务队列中窃取执行
 */
public class T08_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();      // 根据cpu的核心数，决定创建几个线程
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        service.execute(new R(2));
        // 创建的线程为精灵线程， 主线程不阻塞，看不到输出
        // System.in.read();
    }

    static class R implements Runnable{

        int time;
        R(int t) {
            this.time = t;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}
