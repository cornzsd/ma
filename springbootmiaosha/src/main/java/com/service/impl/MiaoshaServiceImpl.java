package com.service.impl;

import com.model.*;
import com.service.GoodsService;
import com.service.MiaoshaServie;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaServiceImpl implements MiaoshaServie {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisServiceImpl redisService;
    @Override
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        boolean success = goodsService.reduceStock(goods);
        if (!success){
            setGoodsOver(goods.getId());
            return null;
        }
        return orderService.createOrder(user,goods);
    }
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }

}
