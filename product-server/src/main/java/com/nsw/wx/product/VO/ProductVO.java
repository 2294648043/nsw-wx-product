package com.nsw.wx.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 产品类目
 * Created by 张维维
 * 2018-10-18 17:04
 */
@Data
public class ProductVO {
    /**标题*/
    @JsonProperty("title")
    private String title;
    /**ID*/
    @JsonProperty("id")
    private Integer id;
    /**产品*/
    @JsonProperty("product")
    List<WeChatProductVO> weChatProductVOS;
}
