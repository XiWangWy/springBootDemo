package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Entity.User;
import com.bless.Repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wangxi on 18/6/28.
 */

@Api(value = "TestController",description = "第一个测试Controller")
@Slf4j
@RestController
@RequestMapping("bless")
public class TestController {

    @Autowired
    private UserRepository myUserRepository;

    @RequestMapping(value = "/",method = {RequestMethod.GET})
    public ModelAndView indexHtml() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/hello",method = {RequestMethod.GET})
    public ModelAndView helloHtml() {
        return new ModelAndView("hello");
    }



    @ApiOperation(value = "测试接口1",notes = "测试接口介绍")
    @ApiImplicitParam(name = "user",value = "用户实体User",required = true,dataType = "User")
    @RequestMapping(value = "test",method = {RequestMethod.POST})
    public User test(@RequestBody User user){
        log.info("~~~~~~~成功~~~~~~");
        return user;
    }

    @ApiOperation(value = "测试接口2",notes = "测试接口介绍")
    @ApiImplicitParam(name = "username",value = "用户名称",required = true,dataType = "JSONObject")
    @RequestMapping(value = "findUser",method = {RequestMethod.POST})
    public User findUser(@RequestBody JSONObject username){
        String name = username.getString("username");
        User user = myUserRepository.findByUserName(name);
        return user;
    }
    




}
