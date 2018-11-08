package com.nsw.wx.product.controller;

import com.nsw.wx.product.common.WeChatProductOutput;
import com.nsw.wx.product.pojo.ShoppingCart;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.redis.RedisService;
import com.nsw.wx.product.server.ShoppingCartService;
import com.nsw.wx.product.util.ResultVOUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加到购物车（用户）
     * @param response
     * @param token()
     * @param id
     * @return
     */
    @RequestMapping("useraddproduct")
    public Object useraddproduct(HttpServletResponse response, @RequestParam(value = "token",required = false) String token,
                                 @RequestParam(value = "id",required = false) int id,
                                 @RequestParam( value = "num",required = false) String num){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        String openid=redisService.get(token);
        System.out.println(token);
        System.out.println(id);
        System.out.println(num);
        int count=shoppingCartService.addshoppingcart(openid,id,num);
        return ResultVOUtil.success(count);
    }
    /**
     * (用户)
     * 根据openid查询购物车信息
     * @param
     * @return
     */
    @RequestMapping("selectiduser")
    public Object userselectidopenid(HttpServletResponse response,@RequestParam(value = "token",required = false) String token){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        String openid= redisService.get(token);
        System.out.println(openid+token);
        List<ShoppingCart> tb=shoppingCartService.findByIdcart(openid);
        return tb;
    }

    /**
     * 删除购物车信息(以id删除)
     * @param
     * @param id
     * @return
     */
    @RequestMapping("deleteproductid")
    public Object deleteproduct(@RequestParam("id") Integer id){
        return ResultVOUtil.success(shoppingCartService.deletecart(id));
    }

    /**
     * 删除购物车信息（产品id删除，这是下订单成功删除购物车信息）
     * @param
     * @param productid
     * @return
     */
    @RequestMapping("cartproductid")
    public Object cartproductid(@RequestParam("productid") Integer productid){
        return ResultVOUtil.success(shoppingCartService.deletecart(productid));
    }
}
