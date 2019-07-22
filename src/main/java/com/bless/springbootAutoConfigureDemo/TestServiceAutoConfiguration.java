package com.bless.springbootAutoConfigureDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangxi on 2019/7/22.
 */
@Configuration
@EnableConfigurationProperties(value = TestProperties.class)
@ConditionalOnClass(MyService.class)
@ConditionalOnProperty(prefix = "wx-test",value = "enabled",matchIfMissing = true)
public class TestServiceAutoConfiguration {

    @Autowired
    TestProperties testProperties;

    @Bean
    @ConditionalOnMissingBean(MyService.class)
    public MyService myService(){
        MyService myService = new MyService();
        myService.setMsg(testProperties.getMsg());
        return  myService;
    }
}
