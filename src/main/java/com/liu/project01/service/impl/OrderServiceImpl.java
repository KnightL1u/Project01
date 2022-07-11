package com.liu.project01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.project01.Exception.GlobalException;
import com.liu.project01.mapper.OrderMapper;
import com.liu.project01.pojo.Order;
import com.liu.project01.pojo.SeckillGoods;
import com.liu.project01.pojo.SeckillOrder;
import com.liu.project01.pojo.User;
import com.liu.project01.service.IGoodsService;
import com.liu.project01.service.IOrderService;
import com.liu.project01.service.ISeckillGoodsService;
import com.liu.project01.service.ISeckillOrderService;
import com.liu.project01.utils.MD5Util;
import com.liu.project01.utils.UUIDUtil;
import com.liu.project01.vo.GoodsVo;
import com.liu.project01.vo.OrderDetailVo;
import com.liu.project01.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    ISeckillGoodsService seckillGoodsService;


    @Autowired
    IGoodsService goodsService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":" + goodsId, str, 60, TimeUnit.SECONDS);

        return str;
    }

    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user == null || goodsId < 0 || StringUtils.isEmpty(path)) return false;


        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }

    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (user == null || goodsId < 0 || StringUtils.isEmpty(captcha)) return false;

        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        return captcha.equals(redisCaptcha);
    }

    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        ValueOperations ops = redisTemplate.opsForValue();
        //获取seckillGoods
        SeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        //更新seckillGoods
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        //更新到数据库
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = stock_count -1")  //减一
                .eq("id", seckillGoods.getId())    //
                .gt("stock_count", 0));//更新的时候要判断是否还有库存

        if (seckillGoods.getStockCount() < 1) {
            ops.set("isStockEmpty:" + goods.getId(), "0");
        }

        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        order.setPayDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);


        //将订单信息存入Redis
        ops.set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(RespBeanEnum.ORDERNOTEXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoodsVo(goodsVo);
        return orderDetailVo;

    }


}
