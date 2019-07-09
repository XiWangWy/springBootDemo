package com.bless.Repository;

import com.bless.Entity.Citizen;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wangxi on 2019/7/8.
 */
public interface CitizenRepository extends MongoRepository<Citizen,Long>{
}
