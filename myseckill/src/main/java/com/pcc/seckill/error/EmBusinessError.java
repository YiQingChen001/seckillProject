package com.pcc.seckill.error;

public enum EmBusinessError implements CommonError{
    PARAMETER_VALIDATION_ERROR(10000,"参数不合法"),
    USER_NOT_EXIST(10001,"该用户不存在"),
    UNKNOWN_ERROR(10002,"未知错误"),
    USER_LOOGIN_FAIL(10003,"该手机号码与密码不符合！"),
    ITEM_NOT_EXIST(10004,"该商品不存在"),
    USER_NOT_LOGIN(10005,"该用户还未登录"),
    PAGE_NOT_FOUND(404,"你要请求的页面好像暂时飘走了...要不试试请求其它页面?"),
    INTERNAL_SERVER_ERROR(500,"服务器出现异常了"),
    ;

    private int errorcode;
    private String errormeaasge;

    EmBusinessError(int errorcode, String errormeaasge) {
        this.errorcode = errorcode;
        this.errormeaasge = errormeaasge;
    }

    @Override
    public int getErrorCode() {
        return this.errorcode;
    }

    @Override
    public String getErrorMessage() {
        return this.errormeaasge;
    }

    @Override
    public CommonError setErrorMsg(String msg) {
        this.errormeaasge=msg;
        return this;
    }

}
