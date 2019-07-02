package com.bless.java8;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.elasticsearch.common.collect.CopyOnWriteHashMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
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
//        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
//        for(URL url : urls){
//            System.out.println(url.toExternalForm());
//        }
        String idName = "aaa";
        String ss = String.format("*%s*",idName);
        System.out.println(ss);

//        Set<String> ss = new HashSet<>();
//        String ssss = "his-body-sign-alert::体征报警::指标中文名";
//        System.out.println(ssss.split("::"));
//
        String test = "心电\n" +
                "HR\n" +
                "PVCs\n" +
                "RR\n" +
                "SpO2\n" +
                "PR\n" +
                "T1\n" +
                "T2\n" +
                "TD\n" +
                "EtN2O\n" +
                "FiN2O\n" +
                "EtIso\n" +
                "FiIso\n" +
                "awRR (GAS)\n" +
                "MAC\n" +
                "EtCO2\n" +
                "FiCO2\n" +
                "EtO2\n" +
                "FiO2\n" +
                "Art-S\n" +
                "Art-M\n" +
                "Art-D\n" +
                "CVP-M\n" +
                "BIS L\n" +
                "BIS R\n" +
                "SQI L\n" +
                "SQI R\n" +
                "SR L\n" +
                "SR R\n" +
                "SEF L\n" +
                "SEF R\n" +
                "EMG L\n" +
                "EMG R\n" +
                "TP L\n" +
                "TP R\n" +
                "BC L\n" +
                "BC R\n" +
                "sBIS L\n" +
                "sBIS R\n" +
                "sEMG L\n" +
                "sEMG R\n" +
                "ASYM\n" +
                "TB\n" +
                "PPV\n" +
                "dPmx\n" +
                "HR平均值\n" +
                "pArt-S\n" +
                "pArt-M\n" +
                "pArt-D\n" +
                "pCVP-M\n" +
                "TOF-Ratio\n" +
                "TOF-Count\n" +
                "T1%\n" +
                "Dia\n" +
                "Mean\n" +
                "Sys\n" +
                "awRR";

        String aa[] = test.split("\n");
        JSONArray array = new JSONArray();
        for (String a: aa) {
            JSONObject object = new JSONObject();
            object.put("name",a);
            object.put("code","quotaNumber");
            array.add(object);
        }

        JSONObject object = new JSONObject();
        object.put("name","体征指标");
        object.put("fields",array);


        String alert = "SPO2\n" +
                "ECG\n" +
                "NIBP\n" +
                "CO2";

        String aaa[] = alert.split("\n");
        JSONArray alertarray = new JSONArray();
        for (String a: aaa) {
            JSONObject objectalert = new JSONObject();
            objectalert.put("name",a);
            objectalert.put("code","quotaChinese");
            alertarray.add(objectalert);
        }

        JSONObject objectalert = new JSONObject();
        objectalert.put("name","体征报警");
        objectalert.put("fields",alertarray);


        JSONArray export = new JSONArray();
        export.add(object);
        export.add(objectalert);

        JSONObject exOb = new JSONObject();
        exOb.put("body-sign",export);

        System.out.println(exOb.toJSONString());

//        TreeMap treeMap = Maps.newTreeMap();
//        String[] tabs = new String[]{"11","22","33"};
//        for (String[] tab = tabs;;) {
//            System.out.println(tab);
//        }
//        ThreadLocal
//        LinkedBlockingQueue
    }

    private R test(Function<T,R> function, T t){
        return function.apply(t);
    }

}
