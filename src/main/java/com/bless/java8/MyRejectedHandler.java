package com.bless.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wangxi
 * Created by wangxi on 2019/6/20.
 */
@Slf4j
public class MyRejectedHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info("失败的任务！" + r.toString() + executor.toString());
    }
}