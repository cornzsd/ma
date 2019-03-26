package com.service.impl;

import com.dao.GoodsDao;
import com.model.GoodsVO;
import com.model.MiaoshaGoods;
import com.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsDao goodsDao;

    @Override
    public List<GoodsVO> getGoodsList() {

        return goodsDao.getGoodsList();
    }

    @Override
    public GoodsVO getGoodsByGoodsId(long goodsId) {
        return goodsDao.getGoodsByGoodsId(goodsId);
    }

    @Override
    public boolean reduceStock(GoodsVO goods) {
        MiaoshaGoods good=new MiaoshaGoods();
        good.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(good);
        return ret > 0;
    }
}
