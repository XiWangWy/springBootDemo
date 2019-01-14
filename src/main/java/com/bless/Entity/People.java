package com.bless.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxi on 2019/1/11.
 */
@Data
@Entity
@Table(name = "people")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class People {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JoinColumn(name = "project_id")
    private Project project;

//    @OneToMany(fetch=FetchType.LAZY,mappedBy = "people")
//    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//    @OneToMany(mappedBy = "people")
//    @JsonBackReference
    @OneToMany(mappedBy = "people")
    private List<Shoe> shoe;
}
