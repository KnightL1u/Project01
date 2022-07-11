package com.liu.project01.vo;//@date :2022/5/6 13:15

import com.liu.project01.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}
