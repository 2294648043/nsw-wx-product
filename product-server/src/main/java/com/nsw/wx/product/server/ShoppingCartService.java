package com.nsw.wx.product.server;

import com.nsw.wx.product.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartService {

    /**（用户）
     * 根据openid查询商品（购物车）
     * @param
     * @return
     */
    List<ShoppingCart> findByIdcart(@Param("openid") String openid);

    /**
     * 根据productid（查询购物车信息）
     * 用于用户添加相同商品的判断
     * @param productid
     * @return
     */
    ShoppingCart selectProductid(int productid );

    /**
     * 删除购物车信息
     * @param id
     * @return
     */
    int deletecart(int id);

    /**
     *   添加购物车信息
     */
    int addshoppingcart(String openid ,int id,String num);

    /**
     * 修改购物车库存
     */
    int updateshoppingcart(ShoppingCart shoppingCart);

    /**
     * 删除购物车信息
     * @param productid
     * @return
     */
    int cartproductid(int productid);
}
