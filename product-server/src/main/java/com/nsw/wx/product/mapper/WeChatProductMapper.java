package com.nsw.wx.product.mapper;

import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * @program: nsw-wx-product
 * @description: 商品相关
 * @author: 张维维
 * @create: 2018-10-18 17:11
 **/

public interface WeChatProductMapper {

    /**
     * 查询所有在架商品列表
     */
    List<TbWeChatProduct> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    List<TbWeChatProduct> findList(List<String> productIdList);
    /**
     * 扣库存（扣/加）
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
//
//    /**
//     * 加库存
//     * @param decreaseStockInputList
//     */
//    void addstock(List<DecreaseStockInput> decreaseStockInputList);
    /**
     * 根据id查询商品
     * @param productIdList
     * @return
     */
    TbWeChatProduct findById(@Param("productIdList") Integer productIdList);

    /**商家
     * 根据登录的企业id查询所有产品
     * 上架(0),下架(1)
     * @param
     * @param enterpriseid
     */
    List<TbWeChatProduct> findAllproduct(@Param("enterpriseid") String enterpriseid,
                                         @Param("productstatus") String productstatus);

    /**
     * 根据id删除当前产品信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Integer id);

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
     * 修改库存
     * @param id
     * @param stock
     * @return
     */
    int SellupdateWeChatProduct(@Param("id")Integer id ,
                                @Param("stock")Integer stock);
    /**
     * 根据分类id的columnid查询
     * @param
     * @return
     */
    List<TbWeChatProduct> selectBycolumnid(@Param("columnid") Integer columnid);


    /**
     * 以产品标题查询产品
     * @param title
     * @return
     */
    TbWeChatProduct findByTitle(String title);

    /**
     * 查询产品展示到页面
     * @return
     */
    List<TbWeChatProduct> isBestlist();




    /**
     * 查询商品列表
     * @param
     * @return
     */
    List<TbWeChatProduct> findByproductid(List<String> productIdList);




}