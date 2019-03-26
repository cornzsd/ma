package com.service;

import com.model.GoodsVO;
import com.model.MiaoshaUser;
import com.model.OrderInfo;

public interface MiaoshaServie {
    OrderInfo miaosha(MiaoshaUser user, GoodsVO goods);
}
