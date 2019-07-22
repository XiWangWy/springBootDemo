package com.bless;

import com.alibaba.fastjson.JSONObject;
//import com.bless.Elasticsearch.ESService;
import com.bless.Entity.Citizen;
import com.bless.Repository.CitizenRepository;
import com.bless.Repository.TestRepository;
import com.bless.Service.TestService;
import com.bless.springbootAutoConfigureDemo.MyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.source.internal.hbm.MappingDocument;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by wangxi on 18/6/28.
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@ServletComponentScan
@EnableRabbit
@EnableScheduling
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10)
//@EnableJpaRepositories("com.bless.Repository")
//@EntityScan("com.bless.Entity")
public class Application implements CommandLineRunner{

    @Autowired
    private TestRepository testRepository;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Autowired
    private TestService testService;

//    @Autowired
//    private ESService esService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private CitizenRepository citizenRepository;

    @Autowired
    private MyService myService;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        // 去掉_class
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),
                new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(mongoDbFactory, converter);
    }

    @Override
    public void run(String... args) throws Exception {
        String hello = myService.sayHello();
        System.out.println(hello);
//        esService.test();
//        testService.es();
//        Test test = new Test();
//        test.setGender(Gender.famale);
//        test.setName("测试");
//        testRepository.save(test);
//
//        Test test1 = new Test();
//        test1.setGender(Gender.male);
//        test1.setName("测试1");
//        testRepository.save(test1);
//
//        Test test2 = new Test();
//        test2.setGender(Gender.unknow);
//        test2.setName("测试3");
//        testRepository.save(test2);
//        log.info(Gender.unknow.name());
//        log.info(""+Gender.unknow.ordinal());

//        JSONObject ob = new JSONObject();
//        ob.put("IdName","123");
//        ob.put("PatientId","123");
//        log.info(objectMapper.convertValue(ob,JSONObject.class).toJSONString());

//        parseLetterTemplate("{{姓名}}肺功能检查结果为{{高危}}，情况危重需要立即处置。");

//        for (int i = 0; i < 100000; i++) {
//            citizenRepository.save(Citizen.of(null,"name--> " + i,null,null,new Date(),"" + i,"警察局","123","zhangsan",2131l,"beijingbeijing"));
//        }

    }

    private void parseLetterTemplate(String letterTemplate){
        String regex = "\\{\\{([^}]*)}}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(letterTemplate);
        while (matcher.find()){
            System.out.println(matcher.group().replaceAll(regex,"$1"));
        }
    }


}
