package com.bless.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by wangxi on 2018/12/21.
 */
@Data
@Entity
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String city;
}
