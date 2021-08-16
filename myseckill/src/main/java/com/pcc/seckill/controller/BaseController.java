package com.pcc.seckill.controller;


import com.pcc.seckill.error.BusinessException;
import com.pcc.seckill.error.EmBusinessError;
import com.pcc.seckill.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice//监视cotroller层的异常
/**
 * 该类用来处理代码抛出的异常，并返回给前端
 * 会判断异常是否是异常枚举类中的枚举值，如果是，就将该枚举值放在返回给前端的数据中
 * 如果不是，就将该异常处理为异常枚举类中的未知错误
 */
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception ex){
        Map<String, Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException=(BusinessException)ex;
            responseData.put("errorCode",businessException.getErrorcode());
            responseData.put("errorMessage",businessException.getErrormeaasge());
        }else{
            responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMessage",EmBusinessError.UNKNOWN_ERROR.getErrorMessage());
        }
        return new CommonReturnType("fail",responseData);
    }
}
