package com.pcc.seckill.controller;

import com.pcc.seckill.controller.vo.UserVo;
import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.response.CommonReturnType;
import com.pcc.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pcc.seckill.service.model.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/get/{id}")
    public CommonReturnType getUser(@PathVariable int id) throws BusinessException {

        UserModel usermodel = userService.getUserById(id);
        //抛出一个自定义的错误状态码异常
        if(usermodel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVo userVo = convertFromUserModel(usermodel);
        return new CommonReturnType("success",userVo);
    }
    public UserVo convertFromUserModel(UserModel userModel){
        if(userModel==null)return null;
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping(value = "/getotp")
    public CommonReturnType getOtp(@RequestParam("telphone") String telphone){
        System.out.println("telphone:"+telphone);

        Random random = new Random();
        int randomInt = random.nextInt(89999);
        randomInt+=10000;
        String otpCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        log.info("电话号码{}的otp为：{}",telphone,otpCode);
        return new CommonReturnType("success",null);
    }

    @PostMapping("/login")
    public CommonReturnType login(@RequestParam("telphone") String telphone,
                                  @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        if(telphone.equals(null)||password.equals(null)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.validateLogin(telphone, encodeByMD5(password));
        //将用户的登录信息放在session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return new CommonReturnType("success",null);
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonReturnType register(@RequestParam("username") String name,
                                     @RequestParam("gender") Integer gender,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("password") String password,
                                     @RequestParam("telphone") String telphone,
                                     @RequestParam("otpCode") String otpCode,
                                     HttpServletRequest httpServletRequest) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String sessionOtpCode = (String) httpServletRequest.getSession().getAttribute(telphone);
        if(sessionOtpCode==null||!sessionOtpCode.equals(otpCode)){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"验证码不正确");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byTelphone");
        userModel.setThridPartyId("无");
        userModel.setEncrptPassword(encodeByMD5(password));
        userService.register(userModel);
        return  new CommonReturnType("success",null);
    }

    private String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 使用MD5加密方式对密码加密
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String result = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return result;
    }
}
