package com.bless.Elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by wangxi on 2019/5/15.
 */
public interface MusicRepository  extends ElasticsearchRepository<Music,Long>{

    List<Music> findByName(String name);

}
