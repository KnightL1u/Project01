package com.liu.project01.controller;


import com.liu.project01.pojo.User;
import com.liu.project01.service.IOrderService;
import com.liu.project01.vo.OrderDetailVo;
import com.liu.project01.vo.RespBean;
import com.liu.project01.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liuzhengwei
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping("detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSIONERROR);
        }

        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);

    }
}
