package com.rabbitmq;

import com.model.*;
import com.service.GoodsService;

import com.service.OrderService;
import com.service.RedisService;
import com.service.impl.MiaoshaServiceImpl;
import com.service.impl.MiaoshaUserServiceImpl;
import com.service.impl.RedisServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaServiceImpl miaoshaService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public  void  receive(String message){
        System.out.println("receive:"+message);
    }
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public  void  miaoshareceive(String message){
        MiaoshaMessage   mm= RedisServiceImpl.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        GoodsVO goods = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goods.getStockCount();

        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user,goods);
    }
    @RabbitListener(queues = MQConfig.TOP_QUEUE1)
    public  void  receive1(byte[] message){
        System.out.println("receive1:"+new String(message));
    }
    @RabbitListener(queues = MQConfig.TOP_QUEUE2)
    public  void  receive2(String message){
        System.out.println("receive2:"+message);
    }
}
