package com.threadPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class T09_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random random = new Random();

    static {
        for (int i=0; i<nums.length; i++) {
            nums[i] = random.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);
        System.out.println(task.join());
        System.in.read();
    }

    // 没有返回值
    /*static class AddTask extends RecursiveAction {
        int start, end;
        AddTask(int s, int e){
            this.start = s;
            this.end = e;
        }

        @Override
        protected void compute() {
            if(end - start < MAX_NUM) {
                long sum = 0L;
                for(int i=start; i<end; i++) {
                    sum += nums[i];
                }
                System.out.println(Thread.currentThread().getName() + "     " + sum);
            } else {
                int middie = start + (end - start)/2;
                AddTask task1 = new AddTask(start, middie);
                AddTask task2 = new AddTask(middie, end);
                task1.fork();
                task2.fork();
            }
        }
    }*/

    // 有返回值
    static class AddTask extends RecursiveTask<Long> {
        int start, end;
        AddTask(int s, int e){
            this.start = s;
            this.end = e;
        }

        @Override
        protected Long compute() {
            if(end - start < MAX_NUM) {
                long sum = 0L;
                for(int i=start; i<end; i++) {
                    sum += nums[i];
                }
                System.out.println(Thread.currentThread().getName() + "   from  " + start + "   end   " + end +  "     " + sum);
                return sum;
            } else {
                int middie = start + (end - start)/2;
                AddTask task1 = new AddTask(start, middie);
                AddTask task2 = new AddTask(middie, end);
                task1.fork();
                task2.fork();
                return task1.join() + task2.join();

            }
        }
    }


}
