package com.liu.project01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.project01.pojo.User;
import com.liu.project01.vo.LoginVo;
import com.liu.project01.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-25
 */
public interface IUserService extends IService<User> {


    //登录
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    //根据Cookie获取用户
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

    RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response);
}
