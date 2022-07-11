package com.liu.project01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.project01.pojo.Goods;
import com.liu.project01.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-27
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
