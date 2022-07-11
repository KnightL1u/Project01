package com.liu.project01.controller;//@date :2022/4/26 15:10

import com.liu.project01.pojo.User;
import com.liu.project01.service.IGoodsService;
import com.liu.project01.service.IUserService;
import com.liu.project01.service.impl.UserServiceImpl;
import com.liu.project01.vo.DetailVo;
import com.liu.project01.vo.GoodsVo;
import com.liu.project01.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    //windows  优化前QPS 625     缓存QPS  2386
    //linux  优化前QPS  303
    //商品列表
    @RequestMapping(value = "toList", produces = "text/html;charset=utf-8")
    @ResponseBody  //返回对象
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();  //从redis拿页面

        //如果redis里面有对应的缓存,直接返回页面
        String goodsList = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(goodsList)) {
            return goodsList;
        }

        //如果redis里面没有页面缓存  手动渲染we
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        goodsList = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);

        if (!StringUtils.isEmpty(goodsList)) {
            valueOperations.set("goodsList", goodsList, 60, TimeUnit.SECONDS);//key   value   过期时间 ,单位
        }
        return goodsList;
    }

    @RequestMapping(value = "toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId
            , HttpServletRequest request, HttpServletResponse response) {
        //redis中获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String details = (String) valueOperations.get("goodsDetails:" + goodsId);


        //如果页面不为空
        if (!StringUtils.isEmpty(details)) {
            return details;
        }


        //如果为空
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int seckillStatus = 0;   //0未开始,1进行中,2已结束
        int remainSeconds = 0;
        if (nowDate.before(startDate))
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        else if (nowDate.after(endDate)) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsVo);
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        details = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (!StringUtils.isEmpty(details)) {
            valueOperations.set("goodsDetail:" + goodsId, details, 60, TimeUnit.SECONDS);
        }
        return details;
    }


    //商品详情页
    @RequestMapping("toDetail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId) {
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int seckillStatus = 0;   //0未开始,1进行中,2已结束
        int remainSeconds = 0;
        if (nowDate.before(startDate))
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        else if (nowDate.after(endDate)) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }

        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(seckillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }
}
