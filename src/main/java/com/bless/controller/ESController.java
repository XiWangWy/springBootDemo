package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Elasticsearch.ESRestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/citizen")
    public List<JSONObject> get(@RequestParam("name") String idName,
                                @RequestParam("page") Integer page,
                                @RequestParam("page_size") Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return esRestService.searchByIdName(idName,pageable);
    }
}
