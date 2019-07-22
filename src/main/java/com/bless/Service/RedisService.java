package com.bless.Service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * Created by wangxi on 2019/7/12.
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedissonClient redissonClient;

    private Long keepTime;

    public Boolean put(String key,String value) {
        RLock rLock = redissonClient.getLock(key);
        boolean success = false;
        try {
            boolean isLock = rLock.tryLock();
            if (!isLock){
                return false;
            }else {
                RMapCache<String,String> cacheMap = redissonClient.getMapCache("test_wx");
                if ("设备占用！".equals(cacheMap.get(key))){
                    log.info("设备【 " + key + " 】设备占用！");
                    success = false;
                }else {
                    cacheMap.put(key,"设备占用！");
                    success = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            rLock.unlock();
        }finally {
            rLock.unlock();
        }
        return success;
    }

    public void refresh(String key) throws InterruptedException {
        RLock rLock = redissonClient.getLock(key);
        boolean isLock = rLock.tryLock(0l,30l,TimeUnit.SECONDS);
        log.info("加锁-->" + ((isLock == true)?"成功":"失败"));
    }


    public String get(String key){
        RMapCache<String,String> cacheMap = redissonClient.getMapCache("test_wx");
        return cacheMap.get(key);
    }

//    @Scheduled(fixedRate = 1000l)
//    public void printLock(){
//        RMapCache<String,String> cacheMap = redissonClient.getMapCache("test_wx");
//        cacheMap.forEach((key, value) -> log.info("设备{}{}",key,value));
//    }


    public void setKeepTime(Long keepTime) {
        this.keepTime = keepTime;
    }

    public Long getKeepTime() {
        return keepTime;
    }
}
