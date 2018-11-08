package com.nsw.wx.product.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class ShoppingCart {

    private Integer id;//主键

    private String title;//标题

    private String photopath;//图片路径

    private BigDecimal price;//价格

    private  String openid;//用户id

    private Integer num;//购买数量

    //    产品id(供购物车使用)
    private Integer productid;
}
