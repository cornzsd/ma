package com.controller;

import com.model.*;
import com.rabbitmq.MQSender;
import com.service.GoodsService;
import com.service.MiaoshaServie;

import com.service.OrderService;
import com.service.RedisService;
import com.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaServie miaoshaServie;
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private MQSender sender;

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    public  Result doMiaosha(Model model,MiaoshaUser miaoshaUser, long goodsId){
        model.addAttribute("user",miaoshaUser);
        if (miaoshaUser==null){
            return  Result.error(CodeMsg.SESSION_ERROR);
        }
        //预秒杀
        Boolean over  = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        long stock= redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if (stock<0){
            localOverMap.put(goodsId,true);
           return Result.error(CodeMsg.MIAO_SHA_OVER);

        }
        //查询是否已经秒杀
   MiaoshaOrder miaoOrder=orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);
    if (miaoOrder!=null){
      /*  model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
        return "miaosha_fail";*/
        return  Result.error(CodeMsg.REPEATE_MIAOSHA);
    }
/*    GoodsVO goodsVO= goodsService.getGoodsByGoodsId(goodsId);
         stock  = goodsVO.getStockCount();
        if (stock<=0){*/
            /*model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";*/
        /*    return  Result.error(CodeMsg.REPEATE_MIAOSHA);
        }*/
        //入队

  //  OrderInfo orderInfo=miaoshaServie.miaosha(miaoshaUser,goodsVO);
      //  model.addAttribute("orderInfo",orderInfo);
      //  model.addAttribute("goods",goodsVO);
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(miaoshaUser);
        mm.setGoodsId(goodsId);
        sender.miaoshasend(mm);
        return Result.success(0);//排队中
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsList = goodsService.getGoodsList();
        for (GoodsVO goods:goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }

    }
}
