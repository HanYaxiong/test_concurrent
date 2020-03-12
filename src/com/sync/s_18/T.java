package com.sync.s_18;

import java.util.concurrent.TimeUnit;

/**
 * 不要以字符串常量作为锁定对象
 * 在下面的例子中m1和m2其实锁定的是同一个对象
 * 这种情况还会发生比较诡异的现象, 比如你用到了一个类库, 在该类库中代码锁定了"hello"
 * 但是你读不到源码, 所以你也在自己的代码中锁定了"hello", 这个时候就有可能发生非常诡异的死锁阻塞
 * 因为你的程序和你的类库不经意之间使用了同一把锁
 *
 */
public class T {

    String s1 = "hello";
    String s2 = "hello";

    public void m1() {
        synchronized (s1) {
        }
    }

    public void m2() {
        synchronized (s2) {
        }
    }
}
