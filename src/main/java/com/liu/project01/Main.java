package com.liu.project01;//@date :2022/5/6 17:37

import com.liu.project01.mapper.OrderMapper;
import com.liu.project01.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class Main {
    @Autowired
    static OrderMapper orderMapper;

    public static void main(String[] args) {
        Order order = new Order();
        order.setUserId(19111111110l);
        order.setGoodsId(1l);
        order.setDeliveryAddrId(0L);
        order.setGoodsName("IPHONE 12 64G");
        order.setGoodsCount(1);
        order.setGoodsPrice(new BigDecimal(479));
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        order.setPayDate(new Date());
        orderMapper.insert(order);
    }
}
