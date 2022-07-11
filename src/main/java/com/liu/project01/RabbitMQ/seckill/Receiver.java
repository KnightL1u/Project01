package com.liu.project01.RabbitMQ.seckill;//@date :2022/5/7 12:06


import com.alibaba.fastjson.JSON;
import com.liu.project01.pojo.SeckillMessage;
import com.liu.project01.pojo.SeckillOrder;
import com.liu.project01.pojo.User;
import com.liu.project01.service.IGoodsService;
import com.liu.project01.service.IOrderService;
import com.liu.project01.vo.GoodsVo;
import com.liu.project01.vo.RespBean;
import com.liu.project01.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


@Slf4j
@Configuration
public class Receiver {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IOrderService orderService;

    //下单操作
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg) {
        log.info("收到消息:" + msg);
        SeckillMessage seckillMessage = JSON.parseObject(msg, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goodsVo.getStockCount() < 1) {
            return;
        }

        //是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue()
                .get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return;
        }

        //下单
        orderService.seckill(user, goodsVo);


    }


}
