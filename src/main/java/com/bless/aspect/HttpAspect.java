package com.bless.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
/**
 * Created by wangxi on 18/7/26.
 * AOP面向切面  处理某些统一拦截的模块
 */
@Aspect
@Component
@Slf4j
public class HttpAspect {




    @Pointcut("execution(public  * com.bless.controller.TestController.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
       log.info("拦截到http  doBefore");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        //url
        log.info("url=>  " + request.getRequestURL());

        //method
        log.info("method=>  " + request.getMethod());

        //ip
        log.info("ip=>  " + request.getRemoteAddr());

        //body
//        log.info("body=>  " + request.getParameterNames());

        //类方法
        log.info("class_method=>  " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
        log.info("参数=>  " +  Arrays.toString(joinPoint.getArgs()));


//        Arrays.stream(joinPoint.getArgs()).forEach(
//                item -> log.info(item.toString())
//        );
    }

    @After("log()")
    public void doAfter(){
        log.info("拦截到http doAfter");
    }

    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterRetruning(Object object){
        log.info("response={}",object.toString());
    }

}
