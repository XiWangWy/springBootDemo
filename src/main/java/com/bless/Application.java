package com.bless;

import com.alibaba.fastjson.JSONObject;
//import com.bless.Elasticsearch.ESService;
import com.bless.Elasticsearch.CitizenEntity;
import com.bless.Elasticsearch.ESRestService;
import com.bless.Entity.Citizen;
import com.bless.Repository.CitizenRepository;
import com.bless.Repository.TestRepository;
import com.bless.Service.TestService;
import com.bless.springbootAutoConfigureDemo.MyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
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
import java.util.Set;
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

    @Autowired
    private ESRestService esRestService;

    @Autowired
    private ObjectMapper objectMapper;

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
        CitizenEntity citizenEntity = new CitizenEntity();
        citizenEntity.setIdNo(1234567L);
        citizenEntity.setName("Bless-1号");
        citizenEntity.setTags(Sets.newHashSet(1L,2L,3L,4L));
        CitizenEntity.CitizenChild citizenChild = new CitizenEntity.CitizenChild();
        citizenChild.setAge(19);
        citizenChild.setChildName(citizenEntity.getName() + "的孩子");

        CitizenEntity.CitizenChild citizenChild2 = new CitizenEntity.CitizenChild();
        citizenChild2.setAge(20);
        citizenChild2.setChildName(citizenEntity.getName() + "的第二个孩子");
        citizenEntity.setChildren(Lists.newArrayList(citizenChild,citizenChild2));

        esRestService.createIndex("bless_citizen_demo2");
        XContentBuilder xContentBuilder = esRestService.createMapping(CitizenEntity.class);
        esRestService.putMapping("bless_citizen_demo2",xContentBuilder);



        esRestService.insertData("bless_citizen_demo2","_doc",null,objectMapper.writeValueAsString(citizenEntity));


    }

}
