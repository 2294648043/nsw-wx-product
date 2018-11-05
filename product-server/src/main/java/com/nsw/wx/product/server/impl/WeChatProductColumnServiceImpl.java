package com.nsw.wx.product.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.enums.ResultEnum;
import com.nsw.wx.product.exception.ProductException;
import com.nsw.wx.product.mapper.WeChatProductColumnMapper;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import com.nsw.wx.product.server.WeChatProductColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Administrator
 * description:
 * path: nsw-wx-product-com.nsw.wx.product.server.impl-WeChatProductColumnServiceImpl
 * date: 2018/10/18/018 19:40
 */
@Service
public class WeChatProductColumnServiceImpl implements WeChatProductColumnService {
    @Autowired
    private WeChatProductColumnMapper weChatProductColumnMapper;

    @Autowired
    private WeChatProductMapper weChatProductMapper;

    @Override
    public List<WeChatProductColumn> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return weChatProductColumnMapper.findByCategoryTypeIn( categoryTypeList);
    }


    /**
     * 查询登录企业显示产品分类信息
     * @param enterpriseid
     * @return
     */
    @Override
    public List<WeChatProductColumn> findAllproductcolumn(String enterpriseid) {
        return weChatProductColumnMapper.findAllproductcolumn(enterpriseid);
    }

    /**
     * 分页显示产品分类信息
     * @param page
     * @param pageSize
     * @param enterpriseid
     * @return
     */
    @Override
    public PageInfo<WeChatProductColumn> pageSelect(int page, int pageSize, String enterpriseid) {
        PageHelper.startPage(page,pageSize);
        List<WeChatProductColumn> findlist = weChatProductColumnMapper.findAllproductcolumn(enterpriseid);
        PageInfo<WeChatProductColumn> pageInfoProductList =  new PageInfo<WeChatProductColumn>(findlist);
        return pageInfoProductList;
    }

    /**
     * 根据id删除产品分类信息
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        //删除前确认该类目底下是否含有产品
        String weChatProducts="[]";
        System.out.println("++++++++++++++++++++++"+weChatProductMapper.selectBycolumnid(id));
        if (!weChatProductMapper.selectBycolumnid(id).isEmpty()){
            throw new ProductException(ResultEnum.PRODUCT_COLUMN_EXIST);
        }
        return weChatProductColumnMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加产品分类信息
     * @param record
     * @return
     */
    @Override
    public int insertWeChatProductColumn(WeChatProductColumn record) {
        //添加前确认是否已经包含有要添加的类目
        WeChatProductColumn updateWeChatProductColumn=weChatProductColumnMapper.findByTitle(record.getTitle());
        if(updateWeChatProductColumn != null){
            throw new ProductException(ResultEnum.PRODUCT_EXIST);
        }
        return weChatProductColumnMapper.insertWeChatProductColumn(record);
    }

    /**
     * 查询产品分类详细信息
     * @param id
     * @return
     */
    @Override
    public WeChatProductColumn selectByPrimaryKey(Integer id) {
        return weChatProductColumnMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改产品分类详细信息
     * @param record
     * @return
     */
    @Override
    public int updateWeChatProductColumn(WeChatProductColumn record){
        //修改前确认是否已经包含有要修改成的产品分类
        WeChatProductColumn updateWeChatProductColumn=weChatProductColumnMapper.findByTitle(record.getTitle());
        if(updateWeChatProductColumn != null){
            throw new ProductException(ResultEnum.PRODUCT_EXIST);
        }
        return weChatProductColumnMapper.updateWeChatProductColumn(record);
    }

    /**
     * 根据产品分类标题查询
     * @param title
     * @return
     */
    @Override
    public WeChatProductColumn findByTitle(String title) {
        return weChatProductColumnMapper.findByTitle(title);
    }

    /**
     * 显示全部产品分类
     * @return
     */
    @Override
    public List<WeChatProductColumn> Allproductcolumn() {
        return weChatProductColumnMapper.Allproductcolumn();
    }


}
