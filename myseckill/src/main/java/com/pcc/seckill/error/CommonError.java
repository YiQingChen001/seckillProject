package com.pcc.seckill.error;

public interface CommonError {
    int getErrorCode();
    String getErrorMessage();
    CommonError setErrorMsg(String msg);
}
