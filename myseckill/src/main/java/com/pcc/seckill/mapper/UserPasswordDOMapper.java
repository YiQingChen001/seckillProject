package com.pcc.seckill.mapper;

import com.pcc.seckill.entity.UserPasswordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);

    UserPasswordDO selectByUserId(Integer userId);


}