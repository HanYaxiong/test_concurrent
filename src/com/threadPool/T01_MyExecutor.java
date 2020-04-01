package com.threadPool;

import java.util.concurrent.*;

/**
 * execute 需要实现
 */
public class T01_MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        // command.run();   // 方法调用
        new Thread(command).start();    // 新线程执行
    }

    public static void main(String[] args) {
        Executor executor = new T01_MyExecutor();
        executor.execute(()-> System.out.println(Thread.currentThread().getName() + ": hello execute"));

    }
}
