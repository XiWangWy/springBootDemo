package com.bless.Elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
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
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.*;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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
    public Boolean putMapping(String indexName,XContentBuilder xContentBuilder){
        PutMappingRequest putMappingRequest = new PutMappingRequest(indexName);
        putMappingRequest.source(xContentBuilder);
        try {
            AcknowledgedResponse acknowledgedResponse  = restHighLevelClient.indices().putMapping(putMappingRequest,RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
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
    public XContentBuilder createMapping(Class<?> baseEntity){
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

            xContentBuilder.startObject();
            createEntityMapping(xContentBuilder.startObject("properties"),baseEntity).endObject();
            xContentBuilder.endObject();
            return xContentBuilder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造es存储对象的结构
     * @param builder
     * @param baseEntity
     * @return
     * @throws IOException
     */
    private XContentBuilder createEntityMapping(XContentBuilder builder,Class<?> baseEntity) throws IOException {
        Field[] fields = baseEntity.getDeclaredFields();
        for (Field field : fields) {
            ESMappingField esMappingField = field.getAnnotation(ESMappingField.class);
            if (Objects.isNull(esMappingField)) continue;
            DataType dataType = esMappingField.type();

            if (dataType.equals(DataType.Nested)){
                builder
                        .startObject(field.getName())
                        .field("type", dataType.name().toLowerCase());
                //嵌套对象处理
                if (Collection.class.isAssignableFrom(field.getType())){
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    Class<?> myClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    createEntityMapping(builder.startObject("properties"),myClass).endObject();
                }else {
                    createEntityMapping(builder.startObject("properties"),field.getType()).endObject();
                }
                builder.endObject();
            }else if (dataType.equals(DataType.Object)){
                //obj类型
                builder.startObject(field.getName());
                createEntityMapping(builder.startObject("properties"),field.getType()).endObject();
                builder.endObject();
            }else {
                //普通字段处理
                builder
                        .startObject(field.getName())
                        .field("type",dataType.name().toLowerCase())
                        .endObject();
            }
        }

        return builder;
    }

    /**
     * 根据名字查询
     * @param name
     * @return
     * @throws IOException
     */
    public List<CitizenEntity> searchChildByName(String name,String index,String doc) throws IOException {
        SearchRequest searchRequest = new SearchRequest();


        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("children.childName",name);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(termQueryBuilder);

        //查找孩子的名字
        NestedQueryBuilder nestedQueryBuilder = new NestedQueryBuilder("children",boolQueryBuilder,ScoreMode.None);



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(nestedQueryBuilder);
        log.info(searchSourceBuilder.toString());

        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(index);
        searchRequest.types(doc);

       SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
       return  Arrays.stream(searchResponse.getHits().getHits()).map(hit -> {
           try {
               String citizenStr = objectMapper.writeValueAsString(hit.getSourceAsMap());
               return JSON.parseObject(citizenStr,CitizenEntity.class);
           } catch (JsonProcessingException e) {
               e.printStackTrace();
           }
           return null;
       }).collect(Collectors.toList());
    }

    /**
     * 根据名字查询
     * @param
     * @return
     * @throws IOException
     */
    public List<CitizenEntity> searchTags(String index,String doc) throws IOException {
        SearchRequest searchRequest = new SearchRequest();


        TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder("tags", Lists.newArrayList(1,2,3,4));

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(termsQueryBuilder);

        //查找孩子的名字
//        NestedQueryBuilder nestedQueryBuilder = new NestedQueryBuilder("tags",boolQueryBuilder,ScoreMode.None);



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        log.info(searchSourceBuilder.toString());

        //根据查询结果做聚合操作
        TermsAggregationBuilder termsAggregationBuilder = new TermsAggregationBuilder("allName",ValueType.STRING);
        termsAggregationBuilder.field("name").size(Integer.MAX_VALUE);
        searchSourceBuilder.aggregation(termsAggregationBuilder);


        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(index);
        searchRequest.types(doc);




        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

//        searchResponse.getAggregations().asMap().entrySet().stream().forEach(entry -> {
//            ParsedStringTerms parsedStringTerms = (ParsedStringTerms) entry.getValue();
//            Map<Object,Long> map = getAggregationMap(parsedStringTerms);
//        });





        return  Arrays.stream(searchResponse.getHits().getHits()).map(hit -> {
            try {
                String citizenStr = objectMapper.writeValueAsString(hit.getSourceAsMap());
                return JSON.parseObject(citizenStr,CitizenEntity.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    /**
     * h获取聚合中统计信息
     * @param parsedTerms
     * @return
     */
    private Map<Object,Long> getAggregationMap(ParsedTerms parsedTerms){
        return parsedTerms.getBuckets()
                .stream()
                .collect(Collectors.toMap(Terms.Bucket::getKeyAsString,Terms.Bucket::getDocCount));
    }

}
