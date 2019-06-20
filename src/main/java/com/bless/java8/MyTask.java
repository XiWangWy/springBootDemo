package com.bless.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.*;

/**
 * Created by wangxi on 2019/6/20.
 */
@Slf4j
@AllArgsConstructor
@Data
public class MyTask<T> implements Runnable {
    private T data;
    @Override
    public void run() {
        log.info("模拟处理任务！！！ ====> Thread Name:  " + Thread.currentThread().getName() + "数据： " + data.toString());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
