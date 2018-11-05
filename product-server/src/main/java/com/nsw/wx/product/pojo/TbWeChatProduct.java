package com.nsw.wx.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品表实体类
 */
@Data
@Getter
@Setter
public class TbWeChatProduct implements Serializable {
    private Integer id;//主键

    private Integer enterpriseid;//企业ID

    private String title;//标题

    private Integer columnid;//columnid

    private String othercolumns;//其他列

    private String photopath;//图片路径

    private BigDecimal price;//价格

    private Integer returnpoint;//返回点

    private String otherprice;//其他价格

    private String attribute;//属性

    private String shortdesc;//快捷方式

    private Integer hits;//击打

    private Boolean iscommend;//可以表扬

    private Boolean isbest;//是最好的

    private Boolean istop;//是否置顶

    private String otherphotos;//其他照片

    private String shorttitle;//短标题

    private Integer orderid;//订单编号

    private Boolean enable;//是否有效
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputtime;//输入时间

    private Date endtime;//终端时间

    private String picture;//图片

    private String otherfield01;//otherfield01

    private String otherfield03;//otherfield03

    private String otherfield02;//otherfield02

    private Integer brandid;//brandid

    private String unit;//单位

    private Integer stock;//库存

    private Integer integral;//完整的

    private String picture01;//picture01

    private BigDecimal balance;//平衡

    private Integer activitytype;//活动类型

    private Long starttime;//开始时间

    private Long overtime;//加班

    private BigDecimal activeprice;//主动价格

    private Integer childenterpriseid;//儿童企业家Id

    private BigDecimal groupprice;//集团价格
    private String content;//neirong

    private Integer activityid;//activityid

    private Integer grouppurchaseid;//集团采购Id

    private String grouppurchaseprices;//团购价格

    private Integer producttype;//商品种类表

    private Integer activitystock;//activitystock

    private Integer salesvolume;//销售量

    private String distributionsetting;//分布设置

    private Integer viewfrequency;//可视频率

    private Integer praisecount;//praisecount

    private BigDecimal deposit;//存包处

    private BigDecimal rent;//租金

    private BigDecimal volume;//体积

    private BigDecimal weight;//重量
//    /**库存*/
//    private  Integer Stock;
    /** 状态, 0正常1下架. */
    private Integer productstatus;

    public TbWeChatProduct() {
    }
    private  Integer openid;//用户id

    private Integer num;//购买数量

//    产品id(供购物车使用)
    private Integer productid;


}