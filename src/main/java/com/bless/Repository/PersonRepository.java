package com.bless.Repository;

import com.bless.Entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxi on 2018/12/6.
 */
@Repository
public interface PersonRepository  extends CrudRepository<Person,Long>{
    List<Person> findByName(String name);

    @Query(value = "select * from Person u where u.name=?1", nativeQuery = true)
    List<Person> findTest(String name);

}
