package com.bless.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by wangxi on 2019/1/2.
 */
@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String color;


    @ManyToOne(optional = false)
    private Person person;

//    同时需要注意，如果需要将公司表对象或者部门表对象转为JSON数据的时候，为了防止出现无限循环包含对方，需要在部门表（多的一方）的引用公司表（少的一方）对象的set方法上写上注解@JsonBackReference
    @JsonBackReference
    public void setPerson(Person person) {
        this.person = person;
    }
}
