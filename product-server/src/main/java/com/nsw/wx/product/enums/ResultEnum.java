package com.nsw.wx.product.enums;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-12-10 23:00
 */
@Getter
public enum ResultEnum {
    PRODUCT_EXIST(0,"商品已存在"),
    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_ERROR(2, "库存有误"),
    PRODUCT_COLUMN_EXIST(3,"该类目下还存在商品"),
    PRODUCT_STOCK_UPDATE(4,"修改库存失败")

    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
