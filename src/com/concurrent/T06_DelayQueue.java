package com.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class T06_DelayQueue {
    static BlockingQueue<MyTask> queue = new DelayQueue();
    static Random random = new Random();

    static class MyTask implements Delayed {
        long runtime;
        String name;

        MyTask(long rt, String name) {
            this.runtime = rt;
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runtime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return name + "   " + runtime;
        }

        @Override
        public int compareTo(Delayed o) {
            if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
                return -1;
            else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
                return 1;
            else
                return 0;
        }


    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask(now + 1000, "t1");
        MyTask t2 = new MyTask(now + 2000, "t2");
        MyTask t3 = new MyTask(now + 500, "t3");
        MyTask t4 = new MyTask(now + 3000, "t4");

        queue.put(t1);
        queue.put(t2);
        queue.put(t3);
        queue.put(t4);

        System.out.println(queue);

        for (int i = 0; i <4 ; i++) {
            System.out.println(queue.take());
        }

    }
}
