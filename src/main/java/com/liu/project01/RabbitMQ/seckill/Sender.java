package com.liu.project01.RabbitMQ.seckill;//@date :2022/5/7 12:05


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendSeckillMessage(String message) {
        log.info("发送消息:" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);

    }
}
