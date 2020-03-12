package com.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T03_SynchronizedList {

    public static void main(String[] args) {
        List list = new ArrayList();
        List synList = Collections.synchronizedList(list);      // 通过Collection给容器加锁
    }
}
