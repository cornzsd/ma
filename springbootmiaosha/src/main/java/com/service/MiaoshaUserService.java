package com.service;

import com.model.CodeMsg;
import com.model.LoginVo;
import com.model.MiaoshaUser;

import javax.servlet.http.HttpServletResponse;

public interface MiaoshaUserService {

    MiaoshaUser getById(long id);
    boolean login(HttpServletResponse response, LoginVo loginVo);
    public MiaoshaUser getByToken(HttpServletResponse response,String token);
}
