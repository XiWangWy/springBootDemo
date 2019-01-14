package com.bless.Repository;

import com.bless.Entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxi on 2019/1/11.
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project,Long>{
    List<Project> findByName(String name);

    @Query(value = "select * from project p where  p.name = ?1",nativeQuery = true)
    List<Project> test(String name);
}
