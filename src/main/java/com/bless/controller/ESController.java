package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Elasticsearch.ESRestService;
import com.bless.Entity.Citizen;
import com.bless.Repository.CitizenRepository;
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
}
