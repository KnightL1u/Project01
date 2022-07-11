package com.liu.project01.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecKillRabbitMQConfig {
    private static final String QUEUE = "seckillQueue";
    private static final String EXCHANGE = "seckillExchange";


    //Topic模式
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
    }


}
