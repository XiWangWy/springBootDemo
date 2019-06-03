package com.bless.Elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;

/**
 * Created by wangxi on 2019/5/15.
 */
@Data
@Document(indexName = "music",type = "songs")
public class Music {

    @Id
    private Long id;

    @Field
    private String name;

    @Field
    private Integer year;

    @Field
    private String lyrics;
}
