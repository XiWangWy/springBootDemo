package com.bless.KnowledgeUtils;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by wangxi on 18/7/18.
 */
@Data
public class KnowlegdeType {
    @Id
    private String id;

    private String word;

    private String type;
}
