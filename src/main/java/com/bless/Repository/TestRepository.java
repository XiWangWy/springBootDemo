package com.bless.Repository;

import com.bless.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangxi on 2019/1/24.
 */
public interface TestRepository extends JpaRepository<Test,Long>{
}
