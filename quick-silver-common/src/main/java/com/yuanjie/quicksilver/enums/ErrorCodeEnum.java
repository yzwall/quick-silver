package com.yuanjie.quicksilver.enums;

import lombok.Data;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/22
 */
public enum ErrorCodeEnum {

    OK(200, "OK");

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ErrorCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
