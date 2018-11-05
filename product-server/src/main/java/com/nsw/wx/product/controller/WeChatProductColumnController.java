package com.nsw.wx.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.VO.WeChatProductColumnVO;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import com.nsw.wx.product.server.WeChatProductColumnService;
import com.nsw.wx.product.util.JsonData;
import com.nsw.wx.product.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**（商家）
 * 后台产品分类
 */
@RestController
@RequestMapping("/api/productcloumn/")
public class WeChatProductColumnController {
    @Autowired
    private WeChatProductColumnService weChatProductColumnService;

    /**
     * 产品分类分页展示
     * @param page
     * @param limit
     * @param enterpriseid
     * @return
     */
    @RequestMapping("list")
    public Object productColumn(@RequestParam(value = "page",required = false) String page,
                                @RequestParam(value = "limit",required = false) String limit,
                                @RequestParam(value = "enterpriseid",required = false) String enterpriseid){
        System.out.println(page+"================"+limit+"++++++++++++"+enterpriseid);
        PageInfo<WeChatProductColumn> pageInfoList = weChatProductColumnService.pageSelect(
                Integer.parseInt(page),Integer.parseInt(limit),enterpriseid);
        List<WeChatProductColumn> productInfoList = pageInfoList.getList();
        List<WeChatProductColumnVO> productInfoVOList = new ArrayList<>();
        for (WeChatProductColumn productInfo : productInfoList){
            WeChatProductColumnVO weChatProductColumnVO = new WeChatProductColumnVO();
            BeanUtils.copyProperties(productInfo, weChatProductColumnVO);
            productInfoVOList.add(weChatProductColumnVO);
        }
        return JsonData.buildSuccess(productInfoVOList);
    }

    @RequestMapping("selectid")
    public Object selectid(@RequestParam("id") int id){
        WeChatProductColumn weChatProductColumn=weChatProductColumnService.selectByPrimaryKey(id);
        WeChatProductColumnVO weChatProductColumnVO=new WeChatProductColumnVO();
        BeanUtils.copyProperties(weChatProductColumn, weChatProductColumnVO);
        return ResultVOUtil.success(weChatProductColumnVO);
    }
    @RequestMapping("deleteid")
    public  Object deleteid(@RequestParam("id") int id){
        return weChatProductColumnService.deleteByPrimaryKey(id);
    }

    @RequestMapping("add")
    public Object addproductcolumn(@RequestBody WeChatProductColumn weChatProductColumn){
        return weChatProductColumnService.insertWeChatProductColumn(weChatProductColumn);
    }

    @RequestMapping("update")
    public Object updateproductcolumn(@RequestBody WeChatProductColumn weChatProductColumn){
        return  weChatProductColumnService.updateWeChatProductColumn(weChatProductColumn);
    }
    /**
     * 前端
     * 产品分类展示
     * @return
     */
    @RequestMapping("listcolumn")
    public Object productColumnlist(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<WeChatProductColumn> productInfoList=weChatProductColumnService.Allproductcolumn();
        List<WeChatProductColumnVO> productInfoVOList = new ArrayList<>();
        for (WeChatProductColumn productInfo : productInfoList){
            WeChatProductColumnVO weChatProductColumnVO = new WeChatProductColumnVO();
            BeanUtils.copyProperties(productInfo, weChatProductColumnVO);
            productInfoVOList.add(weChatProductColumnVO);
        }
        return JSONArray.toJSONString(productInfoVOList);
    }
}
