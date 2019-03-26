package com.service;

import com.model.GoodsVO;

import java.util.List;

public interface GoodsService {
    public List<GoodsVO> getGoodsList();
    public GoodsVO getGoodsByGoodsId(long goodsId);
    public boolean  reduceStock(GoodsVO goods);

}
