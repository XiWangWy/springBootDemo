package com.bless.KnowledgeUtils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * Created by wangxi on 18/7/17.
 */
@Data
public class KnowlegdeEntity {

    //满足条件
    private String If;

    //结果
    private String Then;

    //结构存储
    private JSONObject reasons;

    //项目ID
    @Id
    private String id;

    //所有原文
    private String source;

    //治疗
    private JSONObject treatment;

    //n个症状句子
//    private ArrayList<Sentence> sentences;


}
