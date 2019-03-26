package com.controller;


import com.model.CodeMsg;
import com.model.LoginVo;
import com.model.Result;

import com.service.MiaoshaUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @RequestMapping("to_login")
    public String toLogin(){

        return "login";
    }
    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        System.out.println(loginVo);
       /*     String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();
    if (StringUtils.isBlank(password)){
            return  Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isBlank(mobile)){
            return  Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (IsMobileValidator.validator(mobile)){
            return  Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        miaoshaUserService.login(response, loginVo);
        return Result.success(true);
    }
}
