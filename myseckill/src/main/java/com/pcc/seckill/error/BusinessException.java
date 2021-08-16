package com.pcc.seckill.error;


/**
 * 包装器模式实现
 */
public class BusinessException extends Exception {

    private int errorcode;
    private String errormeaasge;

    public BusinessException(CommonError commonError) {
        this.errorcode=commonError.getErrorCode();
        this.errormeaasge=commonError.getErrorMessage();
    }
    public BusinessException(CommonError commonError,String msg) {
        this.errorcode=commonError.getErrorCode();
        this.errormeaasge=msg;
    }


    public int getErrorcode() {
        return errorcode;
    }

    public String getErrormeaasge() {
        return errormeaasge;
    }
}
