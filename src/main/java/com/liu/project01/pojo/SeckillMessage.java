package com.liu.project01.pojo;//@date :2022/5/7 11:57


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodsId;
}
