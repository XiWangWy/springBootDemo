package com.bless.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by wangxi on 2019/5/15.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bless.Elasticsearch")
public class ESConfig {

}
