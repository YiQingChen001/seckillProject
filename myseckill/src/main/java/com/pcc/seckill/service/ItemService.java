package com.pcc.seckill.service;

import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);
    //用户购买商品后减少相应的库存
    boolean decreaseStock(Integer itemId, Integer amount);
    //用户购买后增加相应的销量
    void increaseSales(Integer id,Integer amount);
}
