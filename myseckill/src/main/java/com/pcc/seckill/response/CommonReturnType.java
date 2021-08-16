package com.pcc.seckill.response;

import lombok.Data;

@Data
/**
 * 定义通用的返回对象
 * status表示返回处理结果："success","fail"
 * 若success，则返回一个正确的json数据等
 * 若fail，则返回通用的错误码格式
 */
public class CommonReturnType {
    private String status;
    private Object data;

    public CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

}
