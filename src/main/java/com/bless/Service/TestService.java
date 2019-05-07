package com.bless.Service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wangxi on 2019/3/27.
 */
@Service
@Slf4j
public class TestService {
    public void es(){


        for (int i = 0; i < 100000; i++) {
            JSONObject object = new JSONObject();
            object.put("name","lalla" + i);
            object.put("year", i);
            object.put("lyrics","lyrics__" + i);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity requestBody = new HttpEntity<>(object, headers);

            String url = "http://localhost:9200/music/songs/" + (i+1);
            JSONObject response = restTemplate.postForEntity(url, requestBody, JSONObject.class).getBody();
            log.info(response.toJSONString());
        }

    }
}
