package com.bless.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by wangxi on 18/7/2.
 */
@Data
public class User {

    @Id
    private Long id;

    private String userName;

    private String passWord;
}
