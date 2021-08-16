package com.pcc.seckill.service.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telphone;
    //登录方式
    private String registerMode;
    //第三方登录方式
    private String thridPartyId;
    //加密的密码
    private String encrptPassword;
}
