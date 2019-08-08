package com.bless.java8;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bless.Elasticsearch.CitizenEntity;
import com.bless.Entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.swagger.models.auth.In;
//import org.elasticsearch.common.collect.CopyOnWriteHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.NioChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/**
 * Created by wangxi on 2019/3/26.
 */
@Slf4j
public class CollectTest<T,R> {


    private static ObjectMapper objectMapper = new ObjectMapper();

   static class DDD{
       private String ss;
       private Long aa;
       private List<Double> cc;
   }
    public static void main(String[] args) throws JsonProcessingException {
//        CollectTest<Integer,String> collectTest = new CollectTest<>();
//
//        Function<Integer,String> function = (intStr) ->  intStr.toString() +  "hh";
//        String ss = collectTest.test(function,15);
//        System.out.println(ss);
//        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
//        for(URL url : urls){
//            System.out.println(url.toExternalForm());
//        }


//        Arrays.stream(DDD.class.getDeclaredFields()).forEach(field -> {
//            if (field.getType().getTypeName().equals(List.class.getTypeName())){
//                System.out.println(field.getType());
//            }
//        });


//        LongAdder longAdder = new LongAdder();
//        longAdder.increment();
////        longAdder.decrement();
//        longAdder.intValue();
//        System.out.println(longAdder.intValue());

//        char ss = '涨';
//        char ss1 = '张';
//        System.out.println( ss - ss1);


        CitizenEntity citizenEntity = new CitizenEntity();
        citizenEntity.setIdNo(1234567L);
        citizenEntity.setName("Bless-1号");
        citizenEntity.setTags(Sets.newHashSet(5L,6L));
        CitizenEntity.CitizenChild citizenChild = new CitizenEntity.CitizenChild();
        citizenChild.setAge(19);
        citizenChild.setChildName(citizenEntity.getName() + "的孩子");

        CitizenEntity.CitizenChild citizenChild2 = new CitizenEntity.CitizenChild();
        citizenChild2.setAge(20);
        citizenChild2.setChildName(citizenEntity.getName() + "的第三个孩子");
        citizenEntity.setChildren(Lists.newArrayList(citizenChild,citizenChild2));


        log.info(objectMapper.writeValueAsString(citizenEntity));

//        String idName = "aaa";
//        String ss = String.format("*%s*",idName);
//        System.out.println(ss);

//        Set<String> ss = new HashSet<>();
//        String ssss = "his-body-sign-alert::体征报警::指标中文名";
//        System.out.println(ssss.split("::"));
//
//        String test = "心电\n" +
//                "HR\n" +
//                "PVCs\n" +
//                "RR\n" +
//                "SpO2\n" +
//                "PR\n" +
//                "T1\n" +
//                "T2\n" +
//                "TD\n" +
//                "EtN2O\n" +
//                "FiN2O\n" +
//                "EtIso\n" +
//                "FiIso\n" +
//                "awRR (GAS)\n" +
//                "MAC\n" +
//                "EtCO2\n" +
//                "FiCO2\n" +
//                "EtO2\n" +
//                "FiO2\n" +
//                "Art-S\n" +
//                "Art-M\n" +
//                "Art-D\n" +
//                "CVP-M\n" +
//                "BIS L\n" +
//                "BIS R\n" +
//                "SQI L\n" +
//                "SQI R\n" +
//                "SR L\n" +
//                "SR R\n" +
//                "SEF L\n" +
//                "SEF R\n" +
//                "EMG L\n" +
//                "EMG R\n" +
//                "TP L\n" +
//                "TP R\n" +
//                "BC L\n" +
//                "BC R\n" +
//                "sBIS L\n" +
//                "sBIS R\n" +
//                "sEMG L\n" +
//                "sEMG R\n" +
//                "ASYM\n" +
//                "TB\n" +
//                "PPV\n" +
//                "dPmx\n" +
//                "HR平均值\n" +
//                "pArt-S\n" +
//                "pArt-M\n" +
//                "pArt-D\n" +
//                "pCVP-M\n" +
//                "TOF-Ratio\n" +
//                "TOF-Count\n" +
//                "T1%\n" +
//                "Dia\n" +
//                "Mean\n" +
//                "Sys\n" +
//                "awRR";
//
//        String aa[] = test.split("\n");
//        JSONArray array = new JSONArray();
//        for (String a: aa) {
//            JSONObject object = new JSONObject();
//            object.put("name",a);
//            object.put("code","quotaNumber");
//            array.add(object);
//        }
//
//        JSONObject object = new JSONObject();
//        object.put("name","体征指标");
//        object.put("fields",array);
//
//
//        String alert = "SPO2\n" +
//                "ECG\n" +
//                "NIBP\n" +
//                "CO2\n" + "HR\n" +
//                "SpO2\n" +
//                "Dia\n" +
//                "PVCs\n" +
//                "PR\n" +
//                "Mean\n" +
//                "Sys\n" +
//                "RR\n" +
//                "awRR\n" +
//                "EtCO2\n" +
//                "FiCO2\n" +
//                "PEEP\n" +
//                "Ppeak\n" +
//                "Pplat\n" +
//                "Pmean\n" +
//                "VTe\n" +
//                "VTi\n" +
//                "VT/kg\n" +
//                "VTe spn\n" +
//                "VTapnea\n" +
//                "MVspn\n" +
//                "fapnea\n" +
//                "fSIMV\n" +
//                "fsigh\n" +
//                "Deltaint.PEEP\n" +
//                "F-Trigger\n" +
//                "Psupp\n" +
//                "Tinsp\n" +
//                "Pinsp\n" +
//                "Trise\n" +
//                "VT\n" +
//                "f\n" +
//                "Tpause\n" +
//                "IBW\n" +
//                "O2%\n" +
//                "MVe\n" +
//                "ftot\n" +
//                "fmand\n" +
//                "fspn\n" +
//                "MVleak\n" +
//                "FiO2\n" +
//                "Cstat\n" +
//                "Cdyn\n" +
//                "RSBI\n" +
//                "Exp%\n" +
//                "Ri\n" +
//                "Re\n" +
//                "RCexp\n" +
//                "NIF\n" +
//                "P0.1\n" +
//                "PEEPi\n" +
//                "NIBP M\n" +
//                "NIBP D\n" +
//                "NIBP S\n" +
//                "flowpeep\n" +
//                "pawpeep\n" +
//                "△int.PEEP";
//
//        String aaa[] = alert.split("\n");
//        JSONArray alertarray = new JSONArray();
//        for (String a: aaa) {
//            JSONObject objectalert = new JSONObject();
//            objectalert.put("name",a);
//            objectalert.put("code","quotaChinese");
//            alertarray.add(objectalert);
//        }
//
//        JSONObject objectalert = new JSONObject();
//        objectalert.put("name","体征报警");
//        objectalert.put("fields",alertarray);
////
////
//        JSONArray export = new JSONArray();
////        export.add(object);
//        export.add(objectalert);
////
////        JSONObject exOb = new JSONObject();
////        exOb.put("body-sign",export);
////
//        System.out.println(objectalert.toJSONString());

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
