package com.liu.project01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.project01.pojo.SeckillOrder;
import com.liu.project01.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-27
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);
}
