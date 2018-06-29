package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxi on 18/6/28.
 */

@RestController
@Slf4j
@RequestMapping("bless")
public class TestController {

    @RequestMapping(value = "test",method = {RequestMethod.POST})
    @ResponseBody
    public String test(@RequestBody JSONObject jsonObject){
        return "测试";
    }
}
