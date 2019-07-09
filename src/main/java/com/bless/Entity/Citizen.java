package com.bless.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;


@Data
@Document
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "of")
public class Citizen extends AuditableEntity {

    @Id
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private String id;

    @Indexed
    @Column(name = "id_no", columnDefinition = "varchar(32) default '' comment '证件号码'")
    private String idNo;

    @Column(name = "id_name", columnDefinition = "varchar(30) default '' comment '证件姓名'")
    private String idName;


    @Column(name = "birth_image", columnDefinition = "varchar(150) default '' comment '出生证明图片'")
    private String birthImage;


    @Column(name = "birthday", columnDefinition = "date comment '出生日期'")
    private Date birthday;


    @Column(name = "phone", columnDefinition = "varchar(20) default '' comment '本人电话'")
    private String phone;


    @Column(name = "work_unit", columnDefinition = "varchar(100) default '' comment '工作单位'")
    private String workUnit;

    @Column(name = "contact_phone", columnDefinition = "varchar(15) default '' comment '联系人电话'")
    private String contactPhone;

    @Column(name = "contact_name", columnDefinition = "varchar(30) default '' comment '联系人姓名'")
    private String contactName;


    @Column(name = "location", columnDefinition = "bigint(15) unsigned default 0 comment '所在地'")
    private Long location;

    @Column(name = "address", columnDefinition = "varchar(200)  default '' comment '家庭住址'")
    private String address;



}
