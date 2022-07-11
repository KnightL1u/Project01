package com.liu.project01.controller;//@date :2022/4/25 13:26


import com.liu.project01.service.IUserService;
import com.liu.project01.vo.LoginVo;
import com.liu.project01.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;


    //跳转登录页面
    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {

        return userService.doLogin(loginVo, request, response);
    }

}
