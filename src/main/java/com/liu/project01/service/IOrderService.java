package com.liu.project01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.project01.pojo.Order;
import com.liu.project01.pojo.User;
import com.liu.project01.vo.GoodsVo;
import com.liu.project01.vo.OrderDetailVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-27
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId, String path);

    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
