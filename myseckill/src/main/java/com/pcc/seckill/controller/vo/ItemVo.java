package com.pcc.seckill.controller.vo;

import lombok.Data;

@Data
public class ItemVo {
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

    //商品是否在秒杀活动中，以及对应的状态：0表示没有秒杀活动，1表示秒杀活动等待开始，2表示进行中
    private Integer promoStatus;

    //秒杀活动价格
    private Double promoPrice;

    //秒杀活动id
    private Integer promoId;

    //秒杀活动开始时间
    private String startDate;

    private String endDate;

}
