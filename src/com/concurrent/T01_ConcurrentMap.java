package com.concurrent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class T01_ConcurrentMap {
    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new ConcurrentHashMap<>();         // 默认把容器分成16段，插入时只锁定其中一段
        // Map<String, String> map = new ConcurrentSkipListMap<>();     // 排好序的，插入效率高
        // Map<String, String> map = new Hashtable<String, String>();   // 添加时会锁定整个对象
        // Map<String, String> map = new HashMap<String, String>();
        //Collections.synchronizedMap()
        Random random = new Random();

        Thread[] threads = new Thread[500];
        CountDownLatch latch = new CountDownLatch(threads.length);

        long start = System.currentTimeMillis();
        for (int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j=0; j<10000; j++) {
                    map.put("a" + random.nextInt(100000), "a" + random.nextInt(10000));
                }
                latch.countDown();
            });
        }
        Arrays.asList(threads).forEach(t->t.start());
        latch.await();
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
