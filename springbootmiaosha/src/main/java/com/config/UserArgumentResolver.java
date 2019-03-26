package com.config;

import com.model.MiaoshaUser;
import com.service.MiaoshaUserService;
import com.service.impl.MiaoshaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz== MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest)nativeWebRequest.getNativeRequest();
        HttpServletResponse response =(HttpServletResponse) nativeWebRequest.getNativeResponse();
        String paraToken = request.getParameter(MiaoshaUserServiceImpl.COOKI_NAME_TOKEN);
        String CookieToken=getCookie(request,MiaoshaUserServiceImpl.COOKI_NAME_TOKEN);
        String token= StringUtils.isBlank(paraToken)?CookieToken:paraToken;
        MiaoshaUser user = miaoshaUserService.getByToken(response, token);
        return user;
    }

    private String getCookie(HttpServletRequest request,String cookiNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null || cookies.length<=0){
            return  null;
        }
        for (Cookie c:cookies) {
            if (c.getName().equals(cookiNameToken)){
                return  c.getValue();
            }
        }
       return  null;
    }

}
