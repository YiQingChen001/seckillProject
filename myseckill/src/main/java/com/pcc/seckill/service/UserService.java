package com.pcc.seckill.service;

import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.service.model.UserModel;

public interface UserService {

    //查询用户
    UserModel getUserById(Integer id);
    //用户注册
    void register(UserModel userModel) throws BusinessException;
    //验证电话号码与密码是否相符
    UserModel validateLogin(String telphone, String password) throws BusinessException;
}
