package com.nsw.wx.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 * Created by 张维维
 * 2018-10-18 16:40
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架"),
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
