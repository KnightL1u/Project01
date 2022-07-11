//package com.liu.project01.RabbitMQ;//@date :2022/5/6 20:28
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class MQSender {
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
////fanout  /   pubsub
//    public void send(Object msg) {
//        log.info("发送消息:" + msg);
//        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
//    }
////direct
//    public void send01(Object msg) {
//        log.info("发送消息RedMsg:" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
//    }
//
//    public void send02(Object msg) {
//        log.info("发送消息GreenMsg:" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
//    }
////topic
//    public void send03(Object msg) {
//        log.info("发送消息:" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "queue.red.msg", msg);
//    }
//
//    public void send04(Object msg) {
//        log.info("发送消息:" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "tall.queue.green", msg);
//    }
//
//    public void send05(Object msg) {
//        log.info("发送消息:" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "hyl.pretty.girl", msg);
//    }
//
//    public void send06(Object msg) {
//        log.info("发送消息:" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "queue.pretty.msg", msg);
//    }
//
//}
