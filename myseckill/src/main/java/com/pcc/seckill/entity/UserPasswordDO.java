package com.pcc.seckill.entity;

import lombok.Data;

@Data
public class UserPasswordDO {
    private Integer id;

    private String encrptPassword;

    private Integer userId;


}