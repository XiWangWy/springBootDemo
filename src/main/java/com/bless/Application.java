package com.bless;

import com.bless.Entity.Test;
import com.bless.Repository.TestRepository;
import com.bless.Service.TestService;
import com.bless.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.bson.internal.Base64;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by wangxi on 18/6/28.
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@ServletComponentScan
@EnableRabbit
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10)
//@EnableJpaRepositories("com.bless.Repository")
//@EntityScan("com.bless.Entity")
public class Application implements CommandLineRunner{

    @Autowired
    private TestRepository testRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Autowired
    private TestService testService;

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
    }

}
