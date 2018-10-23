package com.bless.Repository;

import com.bless.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

/**
 * Created by wangxi on 18/7/2.
 */

@CacheConfig(cacheNames = "users")
public interface UserRepository extends MongoRepository<User,String> {
    @Cacheable(key = "#p0")
    User findByUserName(String username);

    ArrayList<User> findAll();


    @CachePut(key = "#p0.userName")
    User save(User user);
}
