package com.bless.Repository;

import com.bless.Entity.People;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityResult;
import java.util.List;

/**
 * Created by wangxi on 2019/1/11.
 */
public interface PeopleRepository extends CrudRepository<People,Long> {

    List<People> findByName(String name);

//    @Query(value = "SELECT * from people p INNER JOIN shoe s ON p.id = s.people_id",nativeQuery = true)
//    List<People> findTest(String name);

}
