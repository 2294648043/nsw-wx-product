package com.nsw.wx.product.exception;


import com.nsw.wx.product.enums.ResultEnum;

/**
 * 张维维
 * 2018-10-25 20:59
import com.nsw.wx.product.com.nsw.wx.enums.ResultEnum;
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
