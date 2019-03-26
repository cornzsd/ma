package com.controller;

import com.model.*;
import com.service.GoodsService;
import com.service.MiaoshaUserService;
import com.service.RedisService;
import com.service.impl.MiaoshaUserServiceImpl;
import com.service.impl.RedisServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private MiaoshaUserService miaoshaUserService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;
    @RequestMapping(value = "to_list",produces =  "text/html;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletResponse response, HttpServletRequest request, Model model, MiaoshaUser user/*@CookieValue(name= MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String cookieToken,
                       @RequestParam(name =MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String paramToken*/){
       /* if(StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)){
            return  null;
        }
        String token=StringUtils.isBlank(paramToken)?cookieToken:paramToken;
        MiaoshaUser user = miaoshaUserService.getByToken(response, token);*/
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isNotBlank(html)){
            return html;
        }
        model.addAttribute("user",user);
        List<GoodsVO> goodsList = goodsService.getGoodsList();
        model.addAttribute("goodsList",goodsList);
        SpringWebContext springWebContext=new SpringWebContext(request,response,request.getServletContext(), Locale.getDefault(),model.asMap(),applicationContext);
        String temhtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
         if (StringUtils.isNotBlank(temhtml)){
             redisService.set(GoodsKey.getGoodsList,"",temhtml);
         }

          return  temhtml;
        //   return "goods_list";
     //   redisService.set(GoodsKey.getGoodsList,"",)
    }
    @RequestMapping(value = "to_detail2/{goodsId}",produces="text/html")
    @ResponseBody
    public String toGoodsDetail2(HttpServletResponse response, HttpServletRequest request,Model model,
                                MiaoshaUser miaoshaUser,
                                @PathVariable(name = "goodsId") long goodsId){
        String html = redisService.get(GoodsKey.getGoodsDetails, ""+goodsId, String.class);
        if (StringUtils.isNotBlank(html)){
            return html;
        }
        model.addAttribute("user",miaoshaUser);
        GoodsVO  goods=goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        Date startDate = goods.getStartDate();
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long nowAt=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remainSeconds = 0;
        if (nowAt<startAt){
            miaoshaStatus=0;
            remainSeconds=(int)(startAt-nowAt)/1000;
        }else if (nowAt>endAt){
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            miaoshaStatus=1;
            remainSeconds=0;

        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        SpringWebContext springWebContext=new SpringWebContext(request,response,request.getServletContext(), Locale.getDefault(),model.asMap(),applicationContext);
        String temhtml = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
        if (StringUtils.isNotBlank(temhtml)){
            redisService.set(GoodsKey.getGoodsDetails,""+goodsId,temhtml);
        }
       return  temhtml;

    }
    @RequestMapping(value = "to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toGoodsDetail(
                                MiaoshaUser miaoshaUser,
                                @PathVariable(name = "goodsId") long goodsId){
        GoodsVO  goods=goodsService.getGoodsByGoodsId(goodsId);
        Date startDate = goods.getStartDate();
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long nowAt=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remainSeconds = 0;
        if (nowAt<startAt){
            miaoshaStatus=0;
            remainSeconds=(int)(startAt-nowAt)/1000;
        }else if (nowAt>endAt){
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            miaoshaStatus=1;
            remainSeconds=0;
        }
        GoodsDetailVo goodsDetailVo=new GoodsDetailVo();
        goodsDetailVo.setGoodsVO(goods);
        goodsDetailVo.setMiaoshaUser(miaoshaUser);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);

        return  Result.success(goodsDetailVo);

    }
}
