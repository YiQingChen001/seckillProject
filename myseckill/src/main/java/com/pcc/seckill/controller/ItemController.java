package com.pcc.seckill.controller;


import com.pcc.seckill.controller.vo.ItemVo;
import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.response.CommonReturnType;
import com.pcc.seckill.service.ItemService;
import com.pcc.seckill.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public CommonReturnType createItem(@RequestParam("title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") Double price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);
        itemModel.setStock(stock);

        ItemModel item = itemService.createItem(itemModel);
        return new CommonReturnType("success",convertFromItemModelToItemVo(item));

    }


    @GetMapping("/list")
    public CommonReturnType listItem(){
        List<ItemModel> itemModels = itemService.listItem();
        List<ItemVo> itemVos = new ArrayList<>();
        for (ItemModel itemModel : itemModels) {
            ItemVo itemVo = convertFromItemModelToItemVo(itemModel);
            itemVos.add(itemVo);
        }
        return new CommonReturnType("success",itemVos);
    }

    @PostMapping("/get")
    public CommonReturnType get(@RequestParam("id") Integer id){
        ItemModel item = itemService.getItemById(id);
        return new CommonReturnType("success",convertFromItemModelToItemVo(item));

    }


    private ItemVo convertFromItemModelToItemVo(ItemModel itemModel){
        if(itemModel==null)return null;
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel,itemVo);
        //如果该商品存在促销活动
        if(itemModel.getPromoModel()!=null){
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());

            itemVo.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setEndDate(itemModel.getPromoModel().getEndDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        }else{
            itemVo.setPromoStatus(0);
        }
        return itemVo;
    }


}
