package com.concurrent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();   // 适用于写的少，读的特别多的情景，因为每次写都会复制一份，在复制的上面加
        // List<String> list = new ArrayList<>();
        // List<String> list = new Vector<>();

        Random random = new Random();
        Thread[] threads = new Thread[100];

        for(int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
               for (int j=0; j<1000; j++)
                   list.add("a" + random.nextInt(10000));
            });
        }
        runAndComputeTime(threads);
        System.out.println(list.size());
    }

    static void runAndComputeTime(Thread[] threads) {
        long start = System.currentTimeMillis();
        Arrays.asList(threads).forEach(t->t.start());
        Arrays.asList(threads).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
