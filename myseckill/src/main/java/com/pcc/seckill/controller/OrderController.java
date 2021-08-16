package com.pcc.seckill.controller;


import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.response.CommonReturnType;
import com.pcc.seckill.service.OrderService;
import com.pcc.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;


    @PostMapping("/create")
    public CommonReturnType createOrder(@RequestParam("itemId")Integer itemId,
                                        @RequestParam("amount")Integer amount,
                                        @RequestParam(value = "promoId",required = false)Integer promoId) throws BusinessException {


        //判断用户是否登录
        Boolean is_login =(Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(is_login==null||is_login==false){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel login_user =(UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        orderService.createOrder(login_user.getId(),itemId,promoId,amount);

        return new CommonReturnType("success",null);
    }
}
