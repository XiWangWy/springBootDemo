package com.bless.Repository;

import com.bless.Entity.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wangxi on 2018/12/21.
 */
public interface CityRepository  extends CrudRepository<Person,Long> {
}
