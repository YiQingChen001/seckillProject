package com.pcc.seckill.service;

import com.pcc.seckill.service.model.PromoModel;

public interface PromoService {
    //根据商品id查询其促销活动
    PromoModel getPromoByItemId(Integer itemId);
}
