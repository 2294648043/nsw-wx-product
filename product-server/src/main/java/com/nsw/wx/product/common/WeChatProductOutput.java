package com.nsw.wx.product.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 张维维
 * 2018-10-19 9:13
 */
@Data
public class WeChatProductOutput {
    /**商品id*/
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
    private Integer columnid;
    /**图片*/

    private String photopath;
    /**销售量*/
    private Integer salesvolume;
     /**购买数量*/
    private Integer num;
    /**
    产品id(供购物车使用)*/
    private Integer productid;
}
