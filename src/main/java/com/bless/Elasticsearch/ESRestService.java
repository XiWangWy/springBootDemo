package com.bless.Elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Elasticsearch 服务
 * Created by wangxi on 2019/6/20.
 */
@Slf4j
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
        searchSourceBuilder.from((int) pageable.getOffset());

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


    /**
     * ES创建索引
     * @param indexName
     */
    public void createIndex(String indexName){
        if (exists(indexName)) return;
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        try {
            restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public Boolean exists(String indexName){
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(indexName),RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 更新ES中数据结构
     * @param xContentBuilder
     * @return
     */
    public Boolean putMapping(XContentBuilder xContentBuilder){
        PutMappingRequest putMappingRequest = new PutMappingRequest();
        putMappingRequest.source(xContentBuilder);
        try {
            return restHighLevelClient.indices().putMapping(putMappingRequest,RequestOptions.DEFAULT).isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ES插入数据
     * @param indexName
     * @param doc
     * @param id
     * @param content
     */
    public void insertData(String indexName,String doc,String id,String content){
        IndexRequest indexRequest = new IndexRequest(indexName,doc,id);
        indexRequest.source(content, XContentType.JSON);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
            if (indexResponse.getResult().equals(DocWriteResponse.Result.CREATED)){
                log.info("索引--> {}, 文档--> {},内容--> {},ID--> {}   【插入成功】",indexName,doc,content,indexResponse.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ES删除数据
     * @param indexName
     * @param doc
     * @param id
     */
    public void deleteData(String indexName,String doc,String id){
        DeleteRequest deleteRequest = new DeleteRequest(indexName,doc,id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            if (deleteResponse.getResult().equals(DocWriteResponse.Result.DELETED)){
                log.info("索引--> {}, 文档--> {},ID--> {}   【删除成功】",indexName,doc,deleteResponse.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ES更新数据
     * @param indexName
     * @param doc
     * @param id
     * @param content
     */
    public void updateData(String indexName,String doc,String id,String content){
        UpdateRequest updateRequest = new UpdateRequest(indexName,doc,id);
        updateRequest.doc(content,XContentType.JSON);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
            if (updateResponse.getResult().equals(DocWriteResponse.Result.UPDATED)){
                log.info("索引--> {}, 文档--> {},内容--> {},ID--> {}   【更新成功】",indexName,doc,content,updateResponse.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建ES的表结构
     * @param baseEntity
     * @return
     */
    public XContentBuilder createMapping(Class<? extends BaseEntity> baseEntity){
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

            xContentBuilder.startObject();
            createEntityMapping(xContentBuilder,baseEntity);
            xContentBuilder.endObject();
            return xContentBuilder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private XContentBuilder createEntityMapping(XContentBuilder xContentBuilder,Class<? extends BaseEntity> baseEntity){
        Field[] fields = baseEntity.getDeclaredFields();
        for (Field field : fields) {
            ESMappingField esMappingField = field.getAnnotation(ESMappingField.class);
            DataType dataType = esMappingField.type();
            switch (dataType){

            }
        }

        return null;
    }



//    public String getData(String indexName,String doc,String id){
//        GetRequest getRequest = new GetRequest(indexName,doc,id);
//        getRequest.fetchSourceContext(new FetchSourceContext(true,new String[]{},new String[]{}));
//    }

}
