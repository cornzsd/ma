package com.dao;

import com.model.GoodsVO;
import com.model.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("SELECT g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date FROM miaosha_goods mg LEFT JOIN goods g ON g.id=mg.goods_id")
    public List<GoodsVO> getGoodsList();
    @Select("SELECT g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date FROM miaosha_goods mg LEFT JOIN goods g ON g.id=mg.goods_id where g.id=#{goodsId}")
    public GoodsVO getGoodsByGoodsId(@Param("goodsId") long goodsId);
    @Select("update miaosha_goods set stock_count=stock_count-1  where goods_id=#{goodsId}")
    public int  reduceStock(MiaoshaGoods goods);
}
