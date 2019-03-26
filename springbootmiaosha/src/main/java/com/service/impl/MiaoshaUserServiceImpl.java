package com.service.impl;

import com.dao.MiaoshaUserDao;
import com.exception.GlobalException;
import com.model.*;
import com.service.MiaoshaUserService;

import com.utils.MD5Utils;
import com.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {
    public static final String COOKI_NAME_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    @Autowired
    RedisServiceImpl redisService;


    public MiaoshaUser getById(long id){
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
        if (user==null){
            user =  miaoshaUserDao.getById(id);
            redisService.set(UserKey.getById,""+id,user);
        }
        return  user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Utils.fromPassToDbPass(formPass, user.getSalt()));
         miaoshaUserDao.update(toBeUpdate);
         redisService.delete(MiaoshaUserKey.getById, ""+id);
         return  true;
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
    if (loginVo==null){
        throw new GlobalException(CodeMsg.SERVER_ERROR) ;
    }
    String password = loginVo.getPassword();
    String mobile = loginVo.getMobile();
    MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
    if (miaoshaUser==null){
        throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
    }
    String dbPass = MD5Utils.fromPassToDbPass(password, miaoshaUser.getSalt());
     if (!dbPass.equals(miaoshaUser.getPassword())){
         throw new GlobalException(CodeMsg.PASSWORD_ERROR);
     }
     //登录成功写入redis
        String token = UUIDUtil.uuid();
        addCookie(response, token, miaoshaUser);
        return  true;
    }
   public MiaoshaUser getByToken(HttpServletResponse response,String token){
        if (StringUtils.isBlank(token)) return null;
       MiaoshaUser o = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
     if (o!=null){
         addCookie(response,token,o);
     }

     return  o;
    }


    private void addCookie(HttpServletResponse response, String token, MiaoshaUser miaoshaUser) {
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        Cookie cookie=new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setPath("/");
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        response.addCookie(cookie);

    }
}
