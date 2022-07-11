package com.liu.project01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.project01.Exception.GlobalException;
import com.liu.project01.mapper.UserMapper;
import com.liu.project01.pojo.User;
import com.liu.project01.service.IUserService;
import com.liu.project01.utils.CookieUtil;
import com.liu.project01.utils.MD5Util;
import com.liu.project01.utils.UUIDUtil;
import com.liu.project01.utils.ValidatorUtil;
import com.liu.project01.vo.LoginVo;
import com.liu.project01.vo.RespBean;
import com.liu.project01.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    //实现类
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        //参数校验
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//            return RespBean.error(RespBeanEnum.LOGINERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return RespBean.error(RespBeanEnum.MOBILEERROR);
//        }
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGINERROR);
        }

        if (!MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGINERROR);
        }

        //cookie
        String ticket = UUIDUtil.uuid();
        //request.getSession().setAttribute(ticket, user);  //放在Session里
        redisTemplate.opsForValue().set("user:" + ticket, user);    //用户信息放在redis里
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (StringUtils.isEmpty(userTicket)) return null;

        //如果不为null   在设置一次Cookies
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }


    //更新密码
    @Override
    public RespBean updatePassword(String userTicket, String password,
                                   HttpServletRequest request, HttpServletResponse response) {
        User userByCookie = getUserByCookie(userTicket, request, response);

        if (userByCookie == null) {
            throw new GlobalException(RespBeanEnum.MOBILENOTEXIST);
        }

        userByCookie.setPassword(MD5Util.inputPassToDBPass(password, userByCookie.getSlat()));
        int i = userMapper.updateById(userByCookie);

        if (i == 1) {
            redisTemplate.delete("user:" + userTicket);  //删除redis旧缓存
            return RespBean.success();
        }

        return RespBean.error(RespBeanEnum.PASSWORDUPDATEFAIL);
    }
}
