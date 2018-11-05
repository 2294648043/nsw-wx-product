package com.nsw.wx.product.common;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 张维维
 * 2018-10-19 9:13
 */
@Data
public class WeChatProductOutput {
    private Integer id;

    /** 标题. */
    private String title;

    /** 单价. */
    private BigDecimal price;

    /** 库存. */
    private Integer stock;

    /** 描述. */
    private String content;

    /** 小图. */
    private String picture;

    /** 状态, 0正常1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer producttype;
}
