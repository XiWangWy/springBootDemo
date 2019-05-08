package com.bless.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by wangxi on 2019/5/6.
 */
@Api(value = "RabbitMqController",description = "RabbitMqController")
@Slf4j
@RestController
@RequestMapping("/rabbit/")
public class RabbitMqController {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate2;

    @PostMapping("")
    public void sendMessage(@RequestBody JSONObject jsonObject){
        String routeKey = jsonObject.getString("routeKey");
        String exchange = jsonObject.getString("exchange");
        String context = jsonObject.getString("context");
        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;
        log.info("发送消息==》" + context);
        rabbitTemplate.convertAndSend(exchange,routeKey,context);
    }

    @PostMapping("deadLetter")
    public void sendDeadMessage(@RequestBody JSONObject jsonObject){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
//            设置编码
            messageProperties.setContentEncoding("utf-8");
//            设置过期时间10*1000毫秒
//            messageProperties.setExpiration("5000");
            return message;
        };

        String routeKey = jsonObject.getString("routeKey");
        String exchange = jsonObject.getString("exchange");
        String context = jsonObject.getString("context");
        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;
        log.info("发送消息==》" + context);
        rabbitTemplate2.convertAndSend(exchange, routeKey, context, messagePostProcessor,correlationData);
    }


}
