package com.nsw.wx.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品
 * Created by 张维维
 * 2018-10-18 17:04
 */
@Data
public class WeChatProductVO {

    @JsonProperty("id")
    private Integer id;
    /**标题*/
    @JsonProperty("shorttitle")
    private String shorttitle;
    /**价格*/
    @JsonProperty("price")
    private BigDecimal price;
    /**描述*/
    @JsonProperty("title")
    private String title;
    /**图片*/
    @JsonProperty("photopath")
    private String photopath;
    /**销售量*/
    @JsonProperty("salesvolume")
    private String salesvolume;
    /**集团价格*/
    @JsonProperty("groupprice")
    private String groupprice;
    /**其他价格*/
    @JsonProperty("otherprice")
    private String otherprice;
    /**库存*/
    @JsonProperty("stock")
    private String stock;
}
