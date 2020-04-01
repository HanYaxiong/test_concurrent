package com.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 任务拆分执行, 对比多线程和单线程的执行速度
 */
public class T04_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> result = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        MyTask task1 = new MyTask(1, 80000);
        MyTask task2 = new MyTask(80000, 130000);
        MyTask task3 = new MyTask(130000, 170000);
        MyTask task4 = new MyTask(170000, 200000);

        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<List<Integer>> f1 = service.submit(task1);
        Future<List<Integer>> f2 = service.submit(task2);
        Future<List<Integer>> f3 = service.submit(task3);
        Future<List<Integer>> f4 = service.submit(task4);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    static class MyTask implements Callable<List<Integer>>{
        int startPos, endPos;
        MyTask(Integer s, Integer e) {
            this.startPos = s;
            this.endPos = e;
        }

        @Override
        public List<Integer> call() {
            return getPrime(startPos, endPos);
        }
    }

    static boolean isPrime(Integer num) {
        for (int i=2; i<num/2; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end){
        List<Integer> result = new ArrayList<>();
        for(int i=start; i<end; i++) {
            if(isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }
}
