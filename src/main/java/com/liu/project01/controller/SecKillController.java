package com.liu.project01.controller;//@date :2022/4/27 14:58

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.project01.Exception.GlobalException;
import com.liu.project01.RabbitMQ.seckill.Sender;
import com.liu.project01.pojo.Order;
import com.liu.project01.pojo.SeckillMessage;
import com.liu.project01.pojo.SeckillOrder;
import com.liu.project01.pojo.User;
import com.liu.project01.service.IGoodsService;
import com.liu.project01.service.IOrderService;
import com.liu.project01.service.ISeckillOrderService;
import com.liu.project01.vo.GoodsVo;
import com.liu.project01.vo.RespBean;
import com.liu.project01.vo.RespBeanEnum;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.LockInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping("seckill")
public class SecKillController implements InitializingBean {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IOrderService orderServicel;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Sender sender;
    @Autowired
    RedisScript<Long> redisScript;


    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();


    //windows ????????? qps  770
    //linux ????????? qps    306
    @RequestMapping("doSeckill2")
    public String doSeckill2(Model model, User user, Long goodsId) {
        if (user == null) return "login";
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //????????????
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTYSTOCK);
            return "secKillFail";
        }
        //????????????????????????
        SeckillOrder one = seckillOrderService.getOne(
                new QueryWrapper<SeckillOrder>()
                        .eq("user_id", user.getId())
                        .eq("goods_id", goodsId));
        if (one != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATERROR);
            return "secKillFail";
        }

        //??????  ?????????
        Order order = orderServicel.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "OrderDetail";
    }


    @RequestMapping(value = "path", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getPath(User user, Long goodsId, String captcha) {
        if (user == null) return RespBean.error(RespBeanEnum.SESSIONERROR);

        boolean check = orderServicel.checkCaptcha(user, goodsId, captcha);
        if (!check) {
            return RespBean.error(RespBeanEnum.ERRORCAPTCHA);
        }
        String str = orderServicel.createPath(user, goodsId);

        return RespBean.success(str);
    }


    //windows ????????? qps  770      ????????? 3096
    @RequestMapping(value = "/{path}/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, Model model, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSIONERROR);
        }
        ValueOperations ops = redisTemplate.opsForValue();

        boolean check = orderServicel.checkPath(user, goodsId, path);

        if (!check) {
            return RespBean.error(RespBeanEnum.REQUESTILLEGAL);
        }


        SeckillOrder seckillOrder = (SeckillOrder) ops.get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) return RespBean.error(RespBeanEnum.REPEATERROR);

        if (EmptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTYSTOCK);
        }
//        Long stock = ops.decrement("seckillGoods:" + goodsId, 1);  //?????????????????????
        Long stock = (Long) redisTemplate.execute(
                redisScript, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
        if (stock <= 0) {
            EmptyStockMap.put(goodsId, true);
            return RespBean.error(RespBeanEnum.EMPTYSTOCK);
        }

        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);

        sender.sendSeckillMessage(JSON.toJSONString(seckillMessage));

        return RespBean.success(0);



/*        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //????????????
        if (goods.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTYSTOCK);
        }
        //????????????????????????

//        SeckillOrder seckillOrder = seckillOrderService.getOne(
//                new QueryWrapper<SeckillOrder>()
//                        .eq("user_id", user.getId())
//                        .eq("goods_id", goodsId));
        //??????Redis??????
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId());
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATERROR);
        }

        //??????  ?????????
        Order order = orderServicel.seckill(user, goods);
        return RespBean.success(order);*/

    }

    //-1??????   0??????
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user, Long goodsId) {
        if (user == null) return RespBean.error(RespBeanEnum.SESSIONERROR);
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }


    //???????????????   ??????????????????????????????Redis
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) return;

        //?????????????????????????????????Redis
        for (GoodsVo goodsVo : list) {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        }

    }


    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public void verifyCode(User user, Long goodsId, HttpServletResponse response) {
        if (user == null || goodsId < 0) {
            throw new GlobalException(RespBeanEnum.REQUESTILLEGAL);
        }

        //???????????????????????????????????????
        response.setContentType("image/jpg");
        response.setHeader("Pargam", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //???????????????
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32, 3);
        redisTemplate.opsForValue().set("captcha:" + user.getId() + ":" + goodsId,
                captcha.text(),
                300,
                TimeUnit.SECONDS);


        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.error("?????????????????????:" + e.getMessage());
        }

    }
}
