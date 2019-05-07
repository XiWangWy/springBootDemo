package com.bless.Entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * Created by wangxi on 2019/2/13.
 */
@Data
public class Doc {

    @JSONField(serialzeFeatures= {SerializerFeature.WriteNullStringAsEmpty})
    private String name;

    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullNumberAsZero})
    private Integer age;
}
