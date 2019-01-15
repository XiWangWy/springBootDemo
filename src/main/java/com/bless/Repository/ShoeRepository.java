package com.bless.Repository;

import com.bless.Entity.Shoe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wangxi on 2019/1/14.
 */
public interface ShoeRepository extends CrudRepository<Shoe,Long>{
    List<Shoe> findByName(String name);
}
