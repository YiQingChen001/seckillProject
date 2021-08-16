package com.pcc.seckill.service.impl;

import com.pcc.seckill.entity.PromoDO;
import com.pcc.seckill.mapper.PromoDOMapper;
import com.pcc.seckill.service.PromoService;
import com.pcc.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PromoServiceImpl implements PromoService {


    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        PromoModel promoModel = convertFromPromoDOToPromoModel(promoDO);
        if(promoModel==null)return null;

        //DateTime now = new DateTime();
        //判断当前时间是否属于秒杀活动的时间范围内
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }
        else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }
        else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }


    public PromoModel convertFromPromoDOToPromoModel(PromoDO promoDO){
        if(promoDO==null)return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;

    }}
