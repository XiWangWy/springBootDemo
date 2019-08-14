package com.bless.java8;

import com.bless.Elasticsearch.CitizenEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created by wangxi on 2019/8/8.
 */
public class CodeTest {

    public void test(){

    }
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//       CitizenEntity citizenEntity = (CitizenEntity) Class.forName("CitizenEntity").newInstance();
//        CodeTest codeTest = new  CodeTest();
//        codeTest.test();
//
        List<Integer> ss = Lists.newArrayList();
        ss.sort(Comparator.reverseOrder());
        ss.sort(Comparator.comparing(Integer::intValue));


        TruFunction<String,Integer,Double,Wx> truFunction = Wx::new;
        Wx wx =  truFunction.apply("wx",18,176.00);
        System.out.println(wx.toString());
        Lists.newArrayList(wx).stream().collect(Collectors.reducing(0,Wx::getAge,Integer::max));

        ss.stream().reduce(0,Integer::sum);


        Map<Integer,Wx>  wxs =  Lists.newArrayList(wx).stream()
                .collect(
                        Collectors.groupingBy(
                                Wx::getAge,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingInt(Wx::getAge))
                                        ,Optional::get
                                )
                        )
                );

        Lists.newArrayList(wx).stream().collect(Collectors.groupingBy(Wx::getAge,Collectors.mapping(Wx::getAge,Collectors.toList())));
    }


    public interface TruFunction<T,U,V,R> {
        R apply(T t,U u,V v);
    }

    @Data
    public static class Wx{
        private String name;
        private Integer age;
        private Double height;

        public Wx(String name, Integer age, Double height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }
    }
}
