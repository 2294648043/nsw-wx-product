package com.nsw.wx.product.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class updateProduct {
    private Integer id; //id
    private String title;//标题
    private BigDecimal price; //价格
    private String otherprice;//其他价格
    private String shorttitle;//短标题
    private Integer stock;//库存
    private Integer activitytype;//活动类型
    private BigDecimal groupprice;//集团价格
    private BigDecimal volume;//体积
    private BigDecimal weight;//重量

}
