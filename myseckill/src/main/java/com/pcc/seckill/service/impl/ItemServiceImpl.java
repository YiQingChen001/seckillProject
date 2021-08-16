package com.pcc.seckill.service.impl;

import com.pcc.seckill.entity.ItemInfoDO;
import com.pcc.seckill.entity.ItemStockDO;
import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.mapper.ItemInfoDOMapper;
import com.pcc.seckill.mapper.ItemStockDOMapper;
import com.pcc.seckill.service.ItemService;
import com.pcc.seckill.service.PromoService;
import com.pcc.seckill.service.model.ItemModel;
import com.pcc.seckill.service.model.PromoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemInfoDOMapper itemInfoDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        if(itemModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息为空");
        }
        if(
        itemModel.getPrice()==null||
        itemModel.getDescription()==null||
        itemModel.getTitle()==null||
        itemModel.getImgUrl()==null||
        itemModel.getStock()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不完整");
        }
        ItemInfoDO itemInfoDO=convertFromItemModelToItemDo(itemModel);

        itemInfoDOMapper.insertSelective(itemInfoDO);
        itemModel.setId(itemInfoDO.getId());

        ItemStockDO itemStockDO=convertFormItemModelToItemStock(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        return itemModel;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemInfoDO> itemInfoDOS = itemInfoDOMapper.listItem();
        List<ItemModel> itemModels = new ArrayList<>();
        for (ItemInfoDO itemInfoDO : itemInfoDOS) {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemInfoDO.getId());
            ItemModel itemModel = convertToItemModel(itemInfoDO, itemStockDO);
            itemModels.add(itemModel);
        }
        return itemModels;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemInfoDO itemInfoDO = itemInfoDOMapper.selectByPrimaryKey(id);
        if(itemInfoDO==null)return null;
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemInfoDO.getId());

        ItemModel itemModel = convertToItemModel(itemInfoDO, itemStockDO);

        //获取商品秒杀活动的信息
        PromoModel promoModel = promoService.getPromoByItemId(id);
        if(promoModel!=null){
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;

    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow  = itemStockDOMapper.decreaseStock(itemId, amount);
        if(affectedRow>0){
            return true;
        }
        else return false;

    }

    @Override
    @Transactional
    public void increaseSales(Integer id, Integer amount) {
        itemInfoDOMapper.increaseSales(id,amount);
    }


    public ItemInfoDO convertFromItemModelToItemDo(ItemModel itemModel){
        if(itemModel==null)return null;
        ItemInfoDO itemInfoDO = new ItemInfoDO();
        BeanUtils.copyProperties(itemModel,itemInfoDO);
        return itemInfoDO;
    }
    public ItemStockDO convertFormItemModelToItemStock(ItemModel itemModel){
        if(itemModel==null)return null;
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
    public ItemModel convertToItemModel(ItemInfoDO itemInfoDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemInfoDO,itemModel);
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
