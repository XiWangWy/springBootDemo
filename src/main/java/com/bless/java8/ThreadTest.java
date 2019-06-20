package com.bless.java8;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author
 * Created by wangxi on 2019/6/20.
 */
@Slf4j
public class ThreadTest {

    private static final Integer CORE_POOL_SIZE = 4;
    private static final Integer MAX_POOL_SIZE = 8;
    private static final Integer QUEUE_SIZE = 4;
    private static final Integer KEEP_ALIVE_TIME =  3;
    static MyRejectedHandler myRejectedHandler = new MyRejectedHandler();
    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {

        ThreadFactory  threadFactory = new DefaultThreadFactory("my_test_thread");



        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(QUEUE_SIZE),threadFactory,myRejectedHandler);

        threadPoolExecutor.submit(new MyTask("task1"));
        threadPoolExecutor.submit(new MyTask("task2"));
        threadPoolExecutor.submit(new MyTask("task3"));
        threadPoolExecutor.submit(new MyTask("task8"));

        threadPoolExecutor.submit(new MyTask("task4"));
        threadPoolExecutor.submit(new MyTask("task5"));
        threadPoolExecutor.submit(new MyTask("task6"));
        threadPoolExecutor.submit(new MyTask("task7"));

        threadPoolExecutor.submit(new MyTask("task9"));
        threadPoolExecutor.submit(new MyTask("task10"));
        threadPoolExecutor.submit(new MyTask("task11"));
        threadPoolExecutor.submit(new MyTask("task12"));

        threadPoolExecutor.submit(new MyTask("task13"));
        threadPoolExecutor.submit(new MyTask("task14"));

        while (true){
            try {
                Thread.sleep(1000);
                log.info("时间计数：{} 秒,当前活跃线程数：{}" ,atomicInteger.incrementAndGet(),threadPoolExecutor.getActiveCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
