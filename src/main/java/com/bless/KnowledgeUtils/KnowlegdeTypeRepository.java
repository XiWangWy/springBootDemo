package com.bless.KnowledgeUtils;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wangxi on 18/7/18.
 */
public interface KnowlegdeTypeRepository extends MongoRepository<KnowlegdeType, String> {
    public KnowlegdeType findByWord(String word);
}
