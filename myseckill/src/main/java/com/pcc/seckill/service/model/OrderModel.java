package com.pcc.seckill.service.model;


import lombok.Data;

@Data
public class OrderModel {
    //交易单号
    private String id;

    //购买的用户id
    private Integer userId;

    //购买的商品id
    private Integer itemId;

    //购买时商品的单价,若是促销商品，就为促销时的价格
    private Double itemPrice;

    //购买数量
    private Integer amount;

    //购买金额
    private Double orderPrice;

    //若非空，则表示是以秒杀商品方式下单
    private Integer promoId;

}
