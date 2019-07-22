package com.bless.springbootAutoConfigureDemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wangxi on 2019/7/22.
 */
@ConfigurationProperties(prefix = "wx-test")
public class TestProperties {
    private static final String MSG = "springboot";
    private String msg = MSG;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
