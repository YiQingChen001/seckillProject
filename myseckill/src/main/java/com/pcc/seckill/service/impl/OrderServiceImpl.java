package com.pcc.seckill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.pcc.seckill.entity.OrderDO;
import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.mapper.OrderDOMapper;
import com.pcc.seckill.service.ItemService;
import com.pcc.seckill.service.OrderService;
import com.pcc.seckill.service.UserService;
import com.pcc.seckill.service.model.ItemModel;
import com.pcc.seckill.service.model.OrderModel;
import com.pcc.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId,Integer promoId, Integer amount) throws BusinessException {
        //校验下单的商品是否存在
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel==null){
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIST);
        }
        //校验下单的用户是否存在
        UserModel userModel = userService.getUserById(userId);
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不存在");
        }

        if(promoId!=null){

            //校验对应活动是否存在这个适用商品
            if(promoId.intValue()!=itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
            //校验活动是否正在进行中
            else if(itemModel.getPromoModel().getStatus().intValue()!=2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
        }

        //落单减库存
        boolean is_success = itemService.decreaseStock(itemId, amount);
        if(!is_success){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"库存不足");
        }

         //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setId(getOrderNumber());
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setPromoId(promoId);
        if(promoId!=null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }

        orderModel.setOrderPrice((double) amount*orderModel.getItemPrice());

        orderDOMapper.insertSelective(convertFromOrderModelToOrderDO(orderModel));

        itemService.increaseSales(itemId,amount);
        return orderModel;
    }

    //生成订单号
    public String getOrderNumber(){

        //这里使用简单的订单流水号生成方式： 年月日时分秒+随机6位数
        String format = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String numbers = RandomUtil.randomNumbers(6);

        return format+numbers;
    }
    public OrderDO convertFromOrderModelToOrderDO(OrderModel orderModel){
        if(orderModel==null)return null;
        OrderDO orderDO = new OrderDO();
        BeanUtil.copyProperties(orderModel,orderDO);
        return orderDO;
    }


}
