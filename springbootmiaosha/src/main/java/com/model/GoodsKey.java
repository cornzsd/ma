package com.model;

public class GoodsKey extends  BasePrefix {



    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public  static  GoodsKey getGoodsList=new GoodsKey(60,"gl");
    public  static  GoodsKey getGoodsDetails=new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "gs");
}
