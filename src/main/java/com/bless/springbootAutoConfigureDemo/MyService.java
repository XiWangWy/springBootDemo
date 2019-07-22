package com.bless.springbootAutoConfigureDemo;

/**
 * Created by wangxi on 2019/7/22.
 */
public class MyService {
    private String msg;

    public String sayHello(){
        return "Hello " + msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
