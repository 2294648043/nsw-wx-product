package com.nsw.wx.product.server;

import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.common.WeChatProductOutput;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品
 *
 * @author 张维维
 * description:
 * path: nsw-wx-product-com.nsw.wx.product.server-WeChatProductService
 * date: 2018/10/18/018 17:45
 * version: 02.06
 * To change this template use File | Settings | File Templates.
 */

public interface WeChatProductService {

    /**
     * 分页工具(商家使用)
     * page：当前页
     * pageSize：页面容量
     * enterpriseid:企业ID
     * @param
     * @param
     * @return
     */
    PageInfo<TbWeChatProduct> pageSelect( int page,int pageSize, String enterpriseid,String productstatus);



    /**
     * 查询所有在架商品列表
     */
    List<TbWeChatProduct> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    List<WeChatProductOutput> findList(List<String> productIdList);
    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

    /**
     * 加库存
     * @param decreaseStockInputList
     * @return
     */
    public Boolean addstock(List<DecreaseStockInput> decreaseStockInputList);

    /**商家
     * 根据登录的企业id查询所有产品
     * 上架(0),下架(1)分类
     * @param productstatus
     * @param enterpriseid
     */
    List<TbWeChatProduct> findAllproduct( String enterpriseid, String productstatus);

    /**
     * 根据id查询商品
     * @param productIdList
     * @return
     */
    TbWeChatProduct findById(@Param("productIdList") Integer productIdList);

    /**
     * 根据id删除当前产品信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 添加产品信息
     * @param record
     * @return
     */
    int addTbWeChatProduct(TbWeChatProduct record);

    /**
     * 修改产品信息
     * @param record
     * @return
     */
    int updateWeChatProduct(TbWeChatProduct record);
    /**
     * 修改产品信息
     * @param record
     * @return
     */
    int updateWeChatProduct11(TbWeChatProduct record,String photopath);
    /**
     * 根据分类id的columnid查询
     * @param
     * @return
     */
    List<TbWeChatProduct> selectBycolumnid(Integer columnid);

    /**
     * 根据产品分类标题查询
     * @param title
     * @return
     */
    TbWeChatProduct findByTitle(String title);

    /**
     * 查询产品到页面
     * @return
     */
    List<TbWeChatProduct> isBestlist();

    /**
     * 用户添加购物产品
     * openid:用户id
     * id：产品id
     * num：产品数量
     * @param
     * @return
     */
    int UseraddTbWeChatProduct(int openid ,int id,String num);

    /**（用户）
     * 根据openid查询商品（购物车）
     * @param
     * @return
     */
    List<TbWeChatProduct> findByIdUser(Integer openid);

    /**
     * 查询商品列表(productid)
     * @param
     * @return
     */
    List<WeChatProductOutput> findByproductid(List<String> productIdList);
}
