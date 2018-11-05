package com.nsw.wx.product.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 产品分类
 * kxz
 */
@Data
public class WeChatProductColumnVO {
    /*
    主键
     */
    @JsonProperty("id")
    private Integer id;
    /*
    标题
     */
    @JsonProperty("title")
    private String title;
    /*
    parentId
     */
    @JsonProperty("parentid")
    private Integer parentid;

    /*
    属性
     */
    @JsonProperty("attribute")
    private String attribute;

//    @JsonProperty("")
//    private Integer orderid;//订单编号

    /*
    可以表扬
     */
    @JsonProperty("iscommend")
    private Boolean iscommend;

    /*
    是最好的
     */
    @JsonProperty("isbest")
    private Boolean isbest;

    /*
      是否置顶
     */
    @JsonProperty("istop")
    private Boolean istop;

    /*
    是否有效
     */
    @JsonProperty("enable")
    private Boolean enable;

    /*
    输入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("inputtime")
    private Date inputtime;

    /*
    照片路径
     */
    @JsonProperty("photopath")
    private String photopath;


}
