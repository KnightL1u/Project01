//package com.liu.project01.config;//@date :2022/5/6 20:25
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitMQConfig {
//
//
//    @Bean
//    public Queue queue() {
//        return new Queue("queue", true);
//    }
///*
//    //Fanout模式
//    @Bean
//    public Queue queue01() {
//        return new Queue("queue_fanout01");
//    }
//    @Bean
//    public Queue queue02() {
//        return new Queue("queue_fanout02");
//    }
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
//    }
//    @Bean
//    public Binding binding01() {
//        return BindingBuilder.bind(queue01()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding binding02() {
//        return BindingBuilder.bind(queue02()).to(fanoutExchange());
//    }*/
//
///*
//
//    //Direct模式
//    @Bean
//    public Queue queue01() {
//        return new Queue("queue_direct01");
//    }
//
//    @Bean
//    public Queue queue02() {
//        return new Queue("queue_direct02");
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("directExchange");
//    }
//
//    @Bean
//    public Binding binding01() {
//        return BindingBuilder.bind(queue01()).to(directExchange()).with("queue.red");
//    }
//
//    @Bean
//    public Binding binding02() {
//        return BindingBuilder.bind(queue02()).to(directExchange()).with("queue.green");
//    }
//*/
//
//    //Topic模式
//    @Bean
//    public Queue queue01() {
//        return new Queue("queue_topic01");
//    }
//
//    @Bean
//    public Queue queue02() {
//        return new Queue("queue_topic02");
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange("topicExchange");
//    }
//
//    @Bean
//    public Binding binding01() {
//        return BindingBuilder.bind(queue01()).to(topicExchange()).with("*.*.msg");
//    }
//    @Bean
//    public Binding binding02() {
//        return BindingBuilder.bind(queue01()).to(topicExchange()).with("*.pretty.*");
//    }
//
//    @Bean
//    public Binding binding03() {
//        return BindingBuilder.bind(queue02()).to(topicExchange()).with("tall.#");
//    }
//
//
//}
