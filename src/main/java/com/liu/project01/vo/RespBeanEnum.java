package com.liu.project01.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    ERROR(500, "服务端请求出错"),
    SUCCESS(200, "SUCCESS"),

    //登陆模块
    LOGINERROR(500210, "用户名或密码错误"),
    MOBILEERROR(500211, "手机号码格式不正确"),
    BINDERROR(500212, "参数校验错误"),
    MOBILENOTEXIST(5000213, "手机号不存在"),
    PASSWORDUPDATEFAIL(5000214, "更新密码失败"),
    SESSIONERROR(500215, "用户不存在"),
    //秒杀模块
    EMPTYSTOCK(500500, "库存不足"),
    REPEATERROR(500501, "重复抢购"),

    //订单模块
    ORDERNOTEXIST(500301, "订单不存在"),
    REQUESTILLEGAL(500302, "非法请求"),
    ERRORCAPTCHA(5000303, "验证码错误");


    private final Integer code;
    private final String message;
}
