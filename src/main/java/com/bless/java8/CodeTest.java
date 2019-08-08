package com.bless.java8;

import com.bless.Elasticsearch.CitizenEntity;

/**
 * Created by wangxi on 2019/8/8.
 */
public class CodeTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
       CitizenEntity citizenEntity = (CitizenEntity) Class.forName("CitizenEntity").newInstance();
    }
}
