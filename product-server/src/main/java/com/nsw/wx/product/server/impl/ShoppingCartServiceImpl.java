package com.nsw.wx.product.server.impl;

import com.nsw.wx.product.mapper.ShoppingCartMapper;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.ShoppingCart;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.server.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private WeChatProductMapper weChatProductMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public List<ShoppingCart> findByIdcart(String openid) {
        return shoppingCartMapper.findByIdcart(openid);
    }

    @Override
    public ShoppingCart selectProductid(int productid) {
        return shoppingCartMapper.selectProductid(productid);
    }

    @Override
    public int deletecart(int id) {
        return shoppingCartMapper.deletecart(id);
    }

    @Override
    public int addshoppingcart(String openid ,int id,String num) {
        int count=0;
        TbWeChatProduct Tb= weChatProductMapper.findById(id);//查询商品信息
        ShoppingCart shopping=new ShoppingCart();
        ShoppingCart shoppingCart=shoppingCartMapper.selectProductid(id);//查询购物车信息
        if (shoppingCart !=null) {
            //判断购物车是否存在此商品，是则修改原商品数量，否则就直接添加
            if (shoppingCart.getProductid().equals(Tb.getId())) {
                shoppingCart.setNum(Integer.parseInt(num) + shoppingCart.getNum());
                count=shoppingCartMapper.updateshoppingcart(shoppingCart);
            }
        }else {
            shopping.setOpenid(openid);
            shopping.setProductid(id);//产品ID
            shopping.setPhotopath(Tb.getPhotopath());
            shopping.setPrice(Tb.getPrice());
            shopping.setTitle(Tb.getTitle());
            if (num == null || num == "") {
                shopping.setNum(1);
            } else {
                shopping.setNum(Integer.parseInt(num));
            }
            System.out.println(num);
            count =shoppingCartMapper.addshoppingcart(shopping);
        }
        return count;
    }

    @Override
    public int updateshoppingcart(ShoppingCart shoppingCart) {
        return shoppingCartMapper.updateshoppingcart(shoppingCart);
    }

    @Override
    public int cartproductid(int productid) {
        return shoppingCartMapper.cartproductid(productid);
    }

}
