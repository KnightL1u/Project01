package com.liu.project01.vo;//@date :2022/5/6 18:13

import com.liu.project01.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
