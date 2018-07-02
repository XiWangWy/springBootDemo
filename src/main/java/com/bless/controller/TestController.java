package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Entity.User;
import com.bless.Repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxi on 18/6/28.
 */

@Api(value = "TestController",description = "第一个测试Controller")
@Slf4j
@RestController
@RequestMapping("bless")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "测试接口1",notes = "测试接口介绍")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userName",value = "用户名",required = true,dataType = "string"),
//            @ApiImplicitParam(name = "pwd",value = "密码",required = true,dataType = "string")
//    })
    @ApiImplicitParam(name = "user",value = "用户实体User",required = true,dataType = "User")
    @RequestMapping(value = "test",method = {RequestMethod.POST})
//    @ResponseBody
    public String test(@RequestBody User user){
        log.info("~~~~~~~成功~~~~~~");
        return "成功";
    }

    @ApiImplicitParam(name = "username",value = "用户名称",required = true,dataType = "String")
    @RequestMapping(value = "findUser",method = {RequestMethod.POST})
    public User findUser(@RequestBody JSONObject object){
        String name = object.getString("userName");
        User user = userRepository.findbyUserName(name);
        return user;
    }
//
//    @RequestMapping("/hello")
//    public String hello() {
//        return "hello";
//    }

}
