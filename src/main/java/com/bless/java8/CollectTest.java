package com.bless.java8;

import com.google.common.collect.Lists;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by wangxi on 2019/3/26.
 */
public class CollectTest<T,R> {
    public static void main(String[] args) {
        CollectTest<Integer,String> collectTest = new CollectTest<>();

        Function<Integer,String> function = (intStr) ->  intStr.toString() +  "hh";
        String ss = collectTest.test(function,15);
        System.out.println(ss);
    }

    private R test(Function<T,R> function, T t){
        return function.apply(t);
    }
}
