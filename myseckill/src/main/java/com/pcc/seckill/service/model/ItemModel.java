package com.pcc.seckill.service.model;


import lombok.Data;

@Data
public class ItemModel {
    private Integer id;

    //商品名称
    private String title;

    //商品价格
    private Double price;

    //商品的库存
    private Integer stock;

    //商品的描述
    private String description;

    //商品的销量
    private Integer sales;

    //商品描述图片的url
    private String imgUrl;

    //使用聚合模型，将秒杀活动信息聚合到商品信息上，如果promoModel不为空，表示当前秒杀活动还未结束
    private PromoModel promoModel;

}
