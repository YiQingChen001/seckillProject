package com.pcc.seckill.service.impl;

import com.pcc.seckill.entity.UserDO;
import com.pcc.seckill.entity.UserPasswordDO;
import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.mapper.UserDOMapper;
import com.pcc.seckill.mapper.UserPasswordDOMapper;
import com.pcc.seckill.service.UserService;
import com.pcc.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null)return null;
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertToUserDo(userDO,userPasswordDO);
    }

    @Override
    @Transactional //开启事务
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(userModel.getName()==null||
        userModel.getAge()==null||
        userModel.getGender()==null||
        userModel.getTelphone()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserDO userDO = convertFromModelToUserDo(userModel);
        try {
            //在数据库中设置电话号码为唯一索引，避免同一电话号码重复注册
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号重复注册！");

        }

        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertFromModelToUserPasswordDO(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);


    }

    @Override
    public UserModel validateLogin(String telphone, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectBytelphone(telphone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        Integer id = userDO.getId();
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        UserModel userModel = convertToUserDo(userDO, userPasswordDO);
        if(!password.equals(userPasswordDO.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOOGIN_FAIL);
        }

        return userModel;
    }
    //将userModel转换为UserDO
    public UserDO convertFromModelToUserDo(UserModel userModel){
        if(userModel==null)return null;
        UserDO userDO = new UserDO();
        //利用BeanUtils的copyProperties将一个对象复制到另一个对象（根据对象名称赋值）
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
    //将userModel转换为UserPasswordDO
    public UserPasswordDO convertFromModelToUserPasswordDO(UserModel userModel){
        if(userModel==null)return null;
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setUserId(userModel.getId());
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        return userPasswordDO;
    }
    //根据userDO和userPasswordDO得到UserModel
    public UserModel convertToUserDo(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO==null||userPasswordDO==null)return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userPasswordDO,userModel);
        BeanUtils.copyProperties(userDO,userModel);
        return userModel;

    }
}
