package com.liu.project01.controller;


import com.liu.project01.mapper.UserMapper;
import com.liu.project01.pojo.User;
import com.liu.project01.vo.RespBean;
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
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    MQSender mqSender;
//

    //用户信息
    @RequestMapping("info")
    public RespBean infn(User user) {
        return RespBean.success(user);
    }
//
//
//    @RequestMapping("mq")
//    @ResponseBody
//    public void mq() {
//        mqSender.send("Hello");
//    }
//
//    @RequestMapping("mq/fanout")
//    @ResponseBody
//    public void mq01() {
//        mqSender.send("HelloFanout");
//    }
//
//
//    @RequestMapping("mq/direct01")
//    @ResponseBody
//    public void mq02() {
//        mqSender.send01("HelloDirect,Red");
//    }
//
//
//    @RequestMapping("mq/direct02")
//    @ResponseBody
//    public void mq03() {
//        mqSender.send02("HelloDirect,Green");
//    }
//
//    @RequestMapping("mq/topic01")
//    @ResponseBody
//    public void mq04() {
//        mqSender.send05("HelloTopic");
//    }
//
//
//    @RequestMapping("mq/topic02")
//    @ResponseBody
//    public void mq05() {
//        mqSender.send06("HelloTopic");
//    }


}
