package com.pcc.seckill.entity;

import lombok.Data;

@Data
public class OrderDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;

    private Integer promoId;

}