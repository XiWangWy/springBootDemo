package com.bless.java8;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by wangxi on 2019/3/26.
 */
public class CollectTest<T,R> {

    @Autowired
    private ObjectMapper objectMapper;
    public static void main(String[] args) {
//        CollectTest<Integer,String> collectTest = new CollectTest<>();
//
//        Function<Integer,String> function = (intStr) ->  intStr.toString() +  "hh";
//        String ss = collectTest.test(function,15);
//        System.out.println(ss);
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls){
            System.out.println(url.toExternalForm());
        }

    }

    private R test(Function<T,R> function, T t){
        return function.apply(t);
    }

    private static void test(){
        HashMap map = new HashMap();
        map.put("aa","bb");
    }
}
