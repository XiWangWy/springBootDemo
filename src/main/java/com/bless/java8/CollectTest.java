package com.bless.java8;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
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
          int a = 7;
          System.out.println(a & 123);



    }

    private R test(Function<T,R> function, T t){
        return function.apply(t);
    }

    private static void test(){
        HashMap map = new HashMap();
        map.put("aa","bb");
    }
}
