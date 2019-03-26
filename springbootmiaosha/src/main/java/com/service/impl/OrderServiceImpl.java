package com.service.impl;

import com.dao.OrderDao;
import com.model.GoodsVO;
import com.model.MiaoshaOrder;
import com.model.MiaoshaUser;
import com.model.OrderInfo;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(miaoshaUser.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder order=new MiaoshaOrder();
        order.setGoodsId(goods.getId());
        order.setUserId(miaoshaUser.getId());
        order.setOrderId(orderId);
        orderDao.insertMiaoshaOrder(order);
        return orderInfo;
    }


}
