package com.bless.Entity;

import com.bless.enums.*;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by wangxi on 2019/1/24.
 */
@Data
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Convert(converter = com.bless.enums.Converter.class)
    private Gender gender;

}
