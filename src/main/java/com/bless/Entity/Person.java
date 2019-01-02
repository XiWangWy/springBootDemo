package com.bless.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxi on 2018/12/6.
 */
@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer number;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    private Integer shengao;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "person")
    private List<Car> cars;

    public Person(String name, Integer number, Date birthday, Integer shengao) {
        this.name = name;
        this.number = number;
        this.birthday = birthday;
        this.shengao = shengao;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", birthday=" + birthday +
                ", shengao=" + shengao +
                '}';
    }


}
