package com.nsw.wx.product.server;

import com.nsw.wx.product.ProductServerApplicationTests;
import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.common.WeChatProductOutput;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@Component
public class WeChatProductServiceTest  extends ProductServerApplicationTests {
@Autowired
private WeChatProductMapper weChatProductMapper;
@Autowired
private WeChatProductService weChatProductService;
    @Test
    public void findUpAll() {
    }

    @Test
    public void findList() {
        List<WeChatProductOutput> list = weChatProductService.findList(Arrays.asList("17964", "17965"));

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void decreaseStock() {
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("17965", 2);
        weChatProductService.decreaseStock(Arrays.asList(decreaseStockInput));

    }
}