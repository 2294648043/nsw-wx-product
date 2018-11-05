package com.nsw.wx.product.mapper;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeChatProductColumnMapper {
     List<WeChatProductColumn> findByCategoryTypeIn (List<Integer> categoryTypeList);
    /**商家
     * 根据登录的企业id查询所有产品分类
     *
     * @param
     * @param enterpriseid
     */
    List<WeChatProductColumn> findAllproductcolumn(@Param("enterpriseid") String enterpriseid);

    /**
     * 显示全部产品分类
     * @return
     */
    List<WeChatProductColumn> Allproductcolumn();
    /**
     * 根据id删除分类信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Integer id);


    /**
     * 添加产品分类信息
     * @param record
     * @return
     */
    int insertWeChatProductColumn(WeChatProductColumn record);

    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    WeChatProductColumn selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改产品分类信息
     * @param record
     * @return
     */
    int updateWeChatProductColumn(WeChatProductColumn record);

    /**
     * 根据产品分类标题查询信息
     * @param title
     * @return
     */
    WeChatProductColumn findByTitle(String title);

    /**
     * 查询产品父类标题和id
     * @param enterpriseid
     * @return
     */
   List<WeChatProductColumn> SelTitle(@Param("enterpriseid") Integer enterpriseid);
}
