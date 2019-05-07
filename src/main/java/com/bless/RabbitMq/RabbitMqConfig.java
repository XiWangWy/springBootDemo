package com.bless.RabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxi on 2019/5/6.
 */
@Configuration
public class RabbitMqConfig {


    // Direct模式
    @Bean
    public Queue directQueue(){
        return new Queue("direct");
    }

    @Bean
    public Queue helloQueue(){
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, "redirectExchange");
//       x-dead-letter-routing-key    声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, "KEY_R");
//        return new Queue("hello");
        return QueueBuilder.durable("hello").withArguments(args).build();
    }

    //TODO rabbitMq默认的交换机模式 是 direct模式 所以可以不用下面的配置
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }
    //TODO 绑定一个key "direct"，当消息匹配到就会放到这个队列中
    @Bean
    Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }

    @Bean
    Binding bindingExchangeHelloQueue(Queue helloQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(helloQueue).to(directExchange).with("hello");
    }


    //Fanout广播模式
    @Bean
    public Queue queueGuangBo1(){
        return new Queue("GuangBo1");
    }

    @Bean
    public Queue queueGuangBo2(){
        return new Queue("GuangBo2");
    }

    /**
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeQueueGuangBo1(Queue queueGuangBo1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueGuangBo1).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeQueueGuangBo2(Queue queueGuangBo2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueGuangBo2).to(fanoutExchange);
    }

    //Topic 主题模式
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.message.x");
    }

    @Bean
    public Queue queueWx() {
        return new Queue("topic.wx");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //綁定队列 queueMessages() 到 topicExchange 交换机,路由键只接受完全匹配 topic.message 的队列接受者可以收到消息
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
    }

    //綁定队列 queueMessages() 到 topicExchange 交换机,路由键只要是以 topic.message 开头的队列接受者可以收到消息
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.message.#");
    }

    //綁定队列 queueWx() 到 topicExchange 交换机,路由键只要是以 topic 开头的队列接受者可以收到消息
    @Bean
    Binding bindingExchangeWx(Queue queueWx, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueWx).to(topicExchange).with("topic.#");
    }

    //死信队列
    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     *
     * @return the exchange
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange("DL_EXCHANGE").durable(true).build();
    }

    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 声明一个死信队列.
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     *
     * @return the queue
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {

        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, "redirectExchange");
//       x-dead-letter-routing-key    声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, "KEY_R");
        //这两行参数表示 当你的消息变为死信后 会被directExchange交换机根据路由KEY_R转发到相应的绑定队列

        return QueueBuilder.durable("DL_QUEUE").withArguments(args).build();
    }

    /**
     * 定义死信队列转发队列.
     *
     * @return the queue
     */
    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable("REDIRECT_QUEUE").build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "DL_KEY", null);

    }

    /**
     * 定义死信队列交换机
     * @return
     */
    @Bean("redirectExchange")
    public Exchange redirectExchange() {
        return ExchangeBuilder.directExchange("redirectExchange").durable(true).build();
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "redirectExchange", "KEY_R", null);
    }

}
