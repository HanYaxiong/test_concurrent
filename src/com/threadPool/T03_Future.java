package com.threadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class T03_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(()->{
            TimeUnit.SECONDS.sleep(1);
            return 1000;
        });
        new Thread(task).start();
        System.out.println(task.get());
    }
}
