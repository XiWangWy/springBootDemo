package com.bless.Elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxi on 2019/6/20.
 */
@Service
public class ESRestService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * @param idName
     * @param pageable
     * @return
     */
    public List<JSONObject> searchByIdName(String idName, Pageable pageable){


        SearchRequest searchRequest = new SearchRequest();


        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("idName",String.format("*%s*",idName));
        boolQueryBuilder.must(wildcardQueryBuilder);


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(pageable.getPageSize());
        searchSourceBuilder.from(0);

        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("idx_citizen");
        searchRequest.types("_doc");

        try {
           SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

           SearchHit[] searchHits =  searchResponse.getHits().getHits();
            return Arrays.stream(searchHits).map(searchHitFields -> {
                Map<String,Object> map = searchHitFields.getSourceAsMap();
                try {
                    return JSONObject.parseObject(objectMapper.writeValueAsString(map));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new JSONObject();
            }).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
