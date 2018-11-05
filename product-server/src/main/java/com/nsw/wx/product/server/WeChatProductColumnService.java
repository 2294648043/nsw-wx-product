package com.nsw.wx.product.server;

import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.pojo.WeChatProductColumn;

import java.util.List;

public interface WeChatProductColumnService {
    List<WeChatProductColumn> findByCategoryTypeIn (List<Integer> categoryTypeList);

    /**（商家）
     * 查询产品分类信息
     * @param enterpriseid
     * @return
     */
    List<WeChatProductColumn> findAllproductcolumn(String enterpriseid);

    /**
     * 分页工具(商家使用)
     * page：当前页
     * pageSize：页面容量
     * enterpriseid:企业ID
     * @param
     * @param
     * @return
     */
    PageInfo<WeChatProductColumn> pageSelect(int page, int pageSize, String enterpriseid);

    /**
     * 根据id删除分类信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


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
    WeChatProductColumn selectByPrimaryKey(Integer id);

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
     * 显示全部产品分类
     * @return
     */
    List<WeChatProductColumn> Allproductcolumn();
}
