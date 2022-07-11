//package com.liu.project01.RabbitMQ;//@date :2022/5/6 20:32
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class MQReciver {
//    @RabbitListener(queues = "queue")
//    public void receive(Object msg) {
//        log.info("接收消息:" + msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout01")
//    public void receive01(Object msg) {
//        log.info("queue_fanout01 received :" + msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout02")
//    public void receive02(Object msg) {
//        log.info("queue_fanout02 received :" + msg);
//    }
//
//    @RabbitListener(queues = "queue_direct01")
//    public void receive03(Object msg) {
//        log.info("queue_direct01 received :" + msg);
//    }
//
//    @RabbitListener(queues = "queue_direct02")
//    public void receive04(Object msg) {
//        log.info("queue_direct02 received :" + msg);
//    }
//
//
//    @RabbitListener(queues = "queue_topic01")
//    public void receive05(Object msg) {
//        log.info("queue_topic01 received :" + msg);
//    }
//
//    @RabbitListener(queues = "queue_topic02")
//    public void receive06(Object msg) {
//        log.info("queue_topic02 received :" + msg);
//    }
//
//
//}
