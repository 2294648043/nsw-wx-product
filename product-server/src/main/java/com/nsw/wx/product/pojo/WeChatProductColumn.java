package com.nsw.wx.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品分类表实体类
 */

@Data
@Getter
@Setter
public class WeChatProductColumn implements Serializable {
    private Integer id;//主键

    private Integer enterpriseid;//企业id

    private String title;//标题

    private Integer parentid;//parentId

    private String attribute;//属性

    private Integer orderid;//排序

    private Boolean iscommend;//可以表扬

    private Boolean isbest;//是最好的

    private Boolean istop;//是否置顶

    private Boolean enable;//是否有效

    private Date inputtime;//输入时间

    private String photopath;//照片路径

    private String icon;//偶像

    private Integer childenterpriseid;//儿童企业家id

    private String otherfield;//其他领域

    private String shortdesc;//
}