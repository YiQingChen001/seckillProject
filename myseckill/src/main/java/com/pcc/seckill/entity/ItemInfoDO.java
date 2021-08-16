package com.pcc.seckill.entity;

import lombok.Data;

@Data
public class ItemInfoDO {
    private Integer id;

    private String title;

    private String description;

    private Integer sales;

    private String imgUrl;

    private Double price;


}