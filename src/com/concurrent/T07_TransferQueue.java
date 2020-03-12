package com.concurrent;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class T07_TransferQueue {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue();
        /*new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

        //TimeUnit.SECONDS.sleep(3);
        queue.transfer("aaa");      // 必须先启动消费者线程，否则transfer 会阻塞，相当与我放进来的东西必须有人立马接受消费掉

        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
