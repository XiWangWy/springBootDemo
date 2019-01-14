package com.bless.Repository;

import com.bless.Entity.People;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wangxi on 2019/1/11.
 */
public interface PeopleRepository extends CrudRepository<People,Long> {

    List<People> findByName(String name);
}
