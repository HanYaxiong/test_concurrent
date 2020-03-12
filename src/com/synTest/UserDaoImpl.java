package com.synTest;

public class UserDaoImpl implements UserDao {

    @Override
    public void save(String s) {
        System.out.println("invoke save-------->  " + s);
    }
}
