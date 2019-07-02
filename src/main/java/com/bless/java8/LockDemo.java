package com.bless.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wangxi on 2019/6/28.
 */
@Slf4j
public class LockDemo {



    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static int count;


    public static void main(String[] args) {

        new Thread(()-> readWrite()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> test()).start();
        }

    }

    public static void test(){
        log.info("进入读锁===》  ------Waiting----->   {}",Thread.currentThread().getName());
        reentrantReadWriteLock.readLock().lock();
        log.info("获取到读锁===》{}",Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ThreadName:===> " + Thread.currentThread().getName() + "线程值：===>" + count);
        reentrantReadWriteLock.readLock().unlock();
        log.info("释放读锁===》{}",Thread.currentThread().getName());
    }

    public static void modify(){
        log.error("进入写锁===》{}",Thread.currentThread().getName());
        reentrantReadWriteLock.writeLock().lock();

        log.error("获取到写锁===》{}",Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;
        log.error("===写锁打印==>" + count);
        reentrantReadWriteLock.writeLock().unlock();
        log.error("释放写锁===》{}",Thread.currentThread().getName());
    }

    //写锁的降级问题
    public static void readWrite(){
        log.error("进入写锁===》{}",Thread.currentThread().getName());
        reentrantReadWriteLock.writeLock().lock();

        log.error("获取到写锁===》{}",Thread.currentThread().getName());

        count++;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reentrantReadWriteLock.readLock().lock();
        log.warn("获取到读锁===》 {}",Thread.currentThread().getName());
        log.warn("ThreadName:   ===>    " + Thread.currentThread().getName() + "线程值：===>" + count);

        reentrantReadWriteLock.readLock().unlock();
        log.warn("释放读锁===》 {}",Thread.currentThread().getName());

        log.error("===写锁打印==>" + count);
        reentrantReadWriteLock.writeLock().unlock();
        log.error("释放写锁===》 {}",Thread.currentThread().getName());


    }
}
