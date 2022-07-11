package com.liu.project01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.project01.pojo.Goods;
import com.liu.project01.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-27
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
