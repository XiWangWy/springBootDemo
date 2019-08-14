package com.bless.Elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangxi on 2019/6/20.
 */
@Configuration
public class ESRestClientConfig {

    @Bean
    public RestHighLevelClient createRestClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                       new HttpHost("localhost",9200,"http"),
                        new HttpHost("localhost",8200,"http"),
                        new HttpHost("localhost",8000,"http")
                )
        );
    }
}
