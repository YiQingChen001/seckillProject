package com.pcc.seckill.service;

import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.service.model.OrderModel;

public interface OrderService {
    //创建订单
    OrderModel createOrder(Integer userId, Integer itemId,Integer promoId, Integer amount) throws BusinessException;
}
