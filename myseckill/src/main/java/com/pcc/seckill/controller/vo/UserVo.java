package com.pcc.seckill.controller.vo;

import lombok.Data;

/**
 * 如果直接给前端用户返回一个usermodel，使得攻击者可以直接看到密码，还有一些不需要展示的信息
 * 所以要在contr0ller层增加一个viewobject对象
 */
@Data
public class UserVo {
    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telphone;

}
