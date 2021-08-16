package com.pcc.seckill.mapper;

import com.pcc.seckill.entity.ItemInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ItemInfoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemInfoDO record);

    int insertSelective(ItemInfoDO record);

    ItemInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemInfoDO record);

    int updateByPrimaryKey(ItemInfoDO record);

    List<ItemInfoDO> listItem();

    int increaseSales(@Param("id") Integer id,@Param("amount") Integer amount);

}