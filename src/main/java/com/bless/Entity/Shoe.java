package com.bless.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by wangxi on 2019/1/11.
 */
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Shoe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "people_id")
    @JsonBackReference
    private People people;


    public void setPeople(People people) {
        this.people = people;
    }
}
