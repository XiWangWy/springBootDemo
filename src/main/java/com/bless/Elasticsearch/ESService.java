package com.bless.Elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangxi on 2019/5/15.
 */
@Service
@Slf4j
public class ESService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private MusicRepository musicRepository;

    public void test(){
       List<Music> musicList =  musicRepository.findByName("lalla643");
       log.info("查询总数：======》》》》  " + musicList.size());

        Long  count = musicRepository.count();
        log.info("查询总数：======》》》》  " + count);
    }
}
