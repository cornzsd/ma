package com.service;

import com.model.*;

public interface OrderService {
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId,long goodsId);
    public OrderInfo  createOrder(MiaoshaUser miaoshaUser,GoodsVO goods);
}
