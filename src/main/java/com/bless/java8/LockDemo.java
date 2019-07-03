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
    private static Object object = new Object();

    public static void main(String[] args) {


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> test()).start();
        }
        new Thread(()-> modify()).start();

//        sycTest();
//        for (int i = 0; i < 10; i++) {
//           new Thread(()-> readWrite2()).start();
//        }



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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;
        log.error("===写锁打印==>" + count);
        reentrantReadWriteLock.writeLock().unlock();
        log.error("释放写锁===》{}",Thread.currentThread().getName());
    }

    //写锁的降级问题
    public static void readWrite2(){
        log.info("进入写锁===》{}",Thread.currentThread().getName());
        reentrantReadWriteLock.writeLock().lock();

        log.error("获取到写锁===*********{}, 此时 count ----> {} ",Thread.currentThread().getName(),count);
        count++;
        reentrantReadWriteLock.readLock().lock();


        reentrantReadWriteLock.writeLock().unlock();
        log.warn("释放写锁 ===********* {}",Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try{
            log.debug("===打印==> count -------> {},{}       " ,count,Thread.currentThread().getName());
        }
        finally {
            reentrantReadWriteLock.readLock().unlock();
        }

    }

    /**
     * syc 可重入锁
     */
    public static void sycTest(){
        synchronized (object){
            log.info("1111111");
            synchronized(object){
                log.info("22222222");
            }
        }
    }
}
