package com.bless.RabbitMq;

import com.bless.Entity.Message;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * Created by wangxi on 2019/5/6.
 */
@Component
@Slf4j
public class Receiver {

    //Direct模式

    /**
     * 处理hello队列的消息
     * @param message
     */
    @RabbitListener(queues = "hello")
    @RabbitHandler
    public void handleHello(String message) throws Exception {
        System.out.println("Hello消费消息");
        System.out.println(new String(message));
        throw new RuntimeException("抛出异常");
    }

    /**
     * 处理direct队列的消息
     * @param message
     */
    @RabbitListener(queues = "direct")
    @RabbitHandler
    public void handleDirect(String message){
//        log.info("当前线程" + Thread.currentThread().getName() + Thread.currentThread().getId());
        System.out.println("Direct消费消息");
        System.out.println(new String(message));
    }


    //Fanout 广播模式 所有队列均能收到交换所的 消息

    @RabbitListener(queues = "GuangBo1")
    public void handGuangBo1(String message){
        System.out.println("GuangBo1消费消息");
        System.out.println(new String(message));
    }

    @RabbitListener(queues = "GuangBo2")
    public void handGuangBo2(String message){
        System.out.println("GuangBo2消费消息");
        System.out.println(new String(message));
    }

    //Topic 主题模式
    @RabbitListener(queues = "topic.message")
    public void handTopicMessage(String message){
        System.out.println("topic.message消费消息");
        System.out.println(new String(message));
    }

    @RabbitListener(queues = "topic.message.x")
    public void handTopicMessages(String message){
        System.out.println("topic.message.x消费消息");
        System.out.println(new String(message));
    }

    @RabbitListener(queues = "topic.wx")
    public void handTopicWx(String message){
        System.out.println("topic.wx消费消息");
        System.out.println(new String(message));
    }

    //死信队列监听
    @RabbitListener(queues = "REDIRECT_QUEUE")
    public void handDeadMessage(org.springframework.amqp.core.Message message, com.rabbitmq.client.Channel channel) throws IOException {
        System.out.println(new String(message.toString()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println("REDIRECT_QUEUE消费消息");
//        System.out.println(new String(message));
    }

}
