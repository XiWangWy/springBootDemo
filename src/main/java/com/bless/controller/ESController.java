package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Elasticsearch.ESRestService;
import com.bless.Entity.Citizen;
import com.bless.Repository.CitizenRepository;
import com.bless.Service.RedisService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by wangxi on 2019/6/20.
 */
@Api(value = "ESController",description = "ESController")
@Slf4j
@RestController
@RequestMapping("/es")
public class ESController {

    @Autowired
    private ESRestService esRestService;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private RedisService redisService;

    @GetMapping("/citizen")
    public List<JSONObject> get(@RequestParam("name") String idName,
                                @RequestParam("page") Integer page,
                                @RequestParam("page_size") Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return esRestService.searchByIdName(idName,pageable);
    }


    @GetMapping("/citizen/mongo")
    public List<Citizen> get(){
        Long a = System.currentTimeMillis();
        log.info("时间开始：--->{}",a);
        List<Citizen> citizens = citizenRepository.findAll();
        log.info("时间结束：--->{}",System.currentTimeMillis() - a);
        return citizens;
    }

    @GetMapping("/citizen/mongoinsert")
    public void insert(){
        for (int i = 0; i < 100000; i++) {
            citizenRepository.save(Citizen.of(null,"name--> " + i,null,null,new Date(),"" + i,"警察局","123","zhangsan",2131l,"beijingbeijing"));
        }
    }

    @PutMapping("/redis/{key}")
    public String redisPut(@PathVariable(value = "key")String key) throws InterruptedException {
        Boolean ok = redisService.put(key,key);
        if (!ok){
            return key + "正在被占用";
        }
        return "";
    }

    @GetMapping("/redis/{key}")
    public String redis(@PathVariable(value = "key")String key) throws InterruptedException {
        String s = redisService.get(key);
        return s;
    }


    @GetMapping("/redis/refresh/{key}")
    public void reRefresh(@PathVariable(value = "key")String key) throws InterruptedException {
        redisService.refresh(key);
    }


}
