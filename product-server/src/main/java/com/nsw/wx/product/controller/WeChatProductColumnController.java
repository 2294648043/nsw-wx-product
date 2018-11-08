package com.nsw.wx.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.VO.WeChatProductColumnVO;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import com.nsw.wx.product.server.WeChatProductColumnService;
import com.nsw.wx.product.util.JsonData;
import com.nsw.wx.product.util.JsonMap;
import com.nsw.wx.product.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * （商家）
 * 后台产品分类
 */
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
@MultipartConfig
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
    public Object productColumn(HttpServletResponse response, @RequestParam(value = "page", required = false) String page,
                                @RequestParam(value = "limit", required = false) String limit,
                                @RequestParam(value = "enterpriseid", required = false) String enterpriseid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println(page + "================" + limit + "++++++++++++" + enterpriseid);
        PageInfo<WeChatProductColumn> pageInfoList = weChatProductColumnService.pageSelect(
                Integer.parseInt(page), Integer.parseInt(limit), enterpriseid);
        List<WeChatProductColumn> productInfoList = pageInfoList.getList();
        List<WeChatProductColumnVO> productInfoVOList = new ArrayList<>();
        for (WeChatProductColumn productInfo : productInfoList) {
            WeChatProductColumnVO weChatProductColumnVO = new WeChatProductColumnVO();
            BeanUtils.copyProperties(productInfo, weChatProductColumnVO);
            productInfoVOList.add(weChatProductColumnVO);
        }
        long count=pageInfoList.getTotal();
        return JsonData.buildSuccess(count,productInfoVOList);
    }

    /**
     * 前端
     * 产品分类展示
     *
     * @return
     */
    @RequestMapping("listcolumn")
    public Object productColumnlist(HttpServletResponse response,
                                    @RequestParam(value = "page") String page,
                                    @RequestParam(value = "limit") String limit,
                                    @RequestParam(value = "enterpriseid",required = false) String enterpriseid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<WeChatProductColumn> pageInfoList = weChatProductColumnService.pageSelect(
        Integer.parseInt(page),Integer.parseInt(limit),enterpriseid);
        List<WeChatProductColumn> productInfoList = pageInfoList.getList();
        List<WeChatProductColumnVO> productInfoVOList = new ArrayList<>();
        for (WeChatProductColumn productInfo : productInfoList) {
            WeChatProductColumnVO weChatProductColumnVO = new WeChatProductColumnVO();
            BeanUtils.copyProperties(productInfo, weChatProductColumnVO);
            productInfoVOList.add(weChatProductColumnVO);
        }
        long count=pageInfoList.getTotal();
        return JsonData.buildSuccess(count,productInfoList);
    }


    @RequestMapping("selectid")
    public Object selectid(HttpServletResponse response, @RequestParam("id") int id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        WeChatProductColumn weChatProductColumn = weChatProductColumnService.selectByPrimaryKey(id);
        WeChatProductColumnVO weChatProductColumnVO = new WeChatProductColumnVO();
        BeanUtils.copyProperties(weChatProductColumn, weChatProductColumnVO);
        return ResultVOUtil.success(weChatProductColumnVO);
    }

    @RequestMapping("deleteid")
    public Object deleteid(HttpServletResponse response, @RequestParam("id") int id,@RequestParam(value = "photopath",required = false) String photopath) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println("---------->"+photopath);
        return weChatProductColumnService.deleteByPrimaryKey(id,photopath);
    }

    @RequestMapping("add")
    public Object addproductcolumn(HttpServletResponse response, @RequestBody String json_str) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        WeChatProductColumn weChatProductColumn = new JsonMap().string2Obj(json_str, new WeChatProductColumn().getClass());
        weChatProductColumn.setEnterpriseid(248);
        weChatProductColumn.setParentid(1);
        int count =weChatProductColumnService.insertWeChatProductColumn(weChatProductColumn);
        System.out.println("------------->"+count);
        return count;
    }


    @RequestMapping("update")
    public Object updateproductcolumn(HttpServletResponse response, @RequestBody String json_str
                        ,@RequestParam(value = "photopath",required = false) String photopath) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        WeChatProductColumn weChatProductColumn = new JsonMap().string2Obj(json_str, new WeChatProductColumn().getClass());
         int count = weChatProductColumnService.updateWeChatProductColumn(weChatProductColumn,photopath);
        return count;
    }

    /**
     * 新增
     * @param response
     * @param
     * @return
     */
    @RequestMapping("selTitle")
    public Object selTitle(HttpServletResponse response
            ,@RequestParam(value = "enterpriseid",required = false) Integer enterpriseid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<WeChatProductColumn> list= weChatProductColumnService.SelTitle(enterpriseid);
        System.out.println("-------->"+list);
        return list;
    }
    /**
     * 新增
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");

        return  weChatProductColumnService.photopath(file);
    }

}
