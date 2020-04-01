package com.threadPool;

import java.util.concurrent.Executor;

/**
 * 这里面只是方法的调用
 */
public class T01_MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static void main(String[] args) {
        new T01_MyExecutor().execute(()-> System.out.println(Thread.currentThread().getName() + ": hello execute"));
    }
}
