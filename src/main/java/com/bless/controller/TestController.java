package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import com.bless.Entity.JSONResult;
import com.bless.Entity.Person;
import com.bless.Entity.User;
import com.bless.Repository.PersonRepository;
import com.bless.Repository.UserRepository;
import com.bless.Security.AuthenticationException;
import com.bless.Security.JwtTokenUtil;
import com.bless.Service.JwtAuthenticationResponse;
import com.bless.Service.MyUserDetailService;
import com.bless.Utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by wangxi on 18/6/28.
 */

@Api(value = "TestController",description = "第一个测试Controller")
@Slf4j
@RestController
@RequestMapping("/")

public class TestController {

    @Autowired
    private UserRepository myUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsService")
    private MyUserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PersonRepository personRepository;

//    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/",method = {RequestMethod.GET})
    public ModelAndView indexHtml() {
        return new ModelAndView("index");
    }

//    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "hello",method = {RequestMethod.GET})
    public ModelAndView helloHtml() {
        return new ModelAndView("hello");
    }


    @ApiOperation(value = "测试接口1",notes = "测试接口介绍")
    @ApiImplicitParam(name = "user",value = "用户实体User",required = true,dataType = "User")
    @RequestMapping(value = "test",method = {RequestMethod.POST})
    public JSONResult test(@RequestBody User user){
        log.info("~~~~~~~成功~~~~~~");
        return ResultUtil.success(user);
    }

    @GetMapping(value = "search")
    public JSONResult getPerson(@RequestParam("name") String name){
//        List<Person> personList = personRepository.findByName(name);
        List<Person> personList = personRepository.findTest(name);
        return ResultUtil.success(personList);
    }



    @PostMapping(value = "getall")
    @PreAuthorize("hasRole('ADMIN')")
    public JSONResult getUsers(){
        return ResultUtil.success(myUserRepository.findAll());
    }





    @PostMapping(value = "auth")
    public JSONResult login(@RequestBody User user){
        authenticate(user.getUserName(), user.getPassWord());


        final UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResultUtil.success(new JwtAuthenticationResponse(token));
    }


    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }



}
