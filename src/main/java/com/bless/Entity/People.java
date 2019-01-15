package com.bless.Entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.*;
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
public class People{

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

    @OneToMany(mappedBy = "people")
    //该注解解决序列化的时候 出现循环引用的问题 必须最后http结果JSONResult 集成JSONObject 使用该注解才会有效果
    @JsonIgnoreProperties("people")
    private List<Shoe> shoe;

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", shoe=" + shoe +
                '}';
    }
}
