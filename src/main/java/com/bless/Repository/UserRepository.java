package com.bless.Repository;

import com.bless.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wangxi on 18/7/2.
 */
public interface UserRepository extends MongoRepository<User,String> {
    User findbyUserName(String username);
}
