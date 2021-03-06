package com.nsw.wx.product.server.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.enums.ResultEnum;
import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.common.WeChatProductOutput;
import com.nsw.wx.product.exception.ProductException;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.redis.RedisService;
import com.nsw.wx.product.redis.WeChatProductOutputKey;
import com.nsw.wx.product.server.WeChatProductService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品
 * @author 张维维
 * description:
 * path: nsw-wx-product-com.nsw.wx.product.server.impl-WeChatProductServiceImpl
 * date: 2018/10/18/018 19:22
 */
@Service
public class WeChatProductServiceImpl implements WeChatProductService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private WeChatProductMapper weChatProductMapper;

    /**
     * 查询上架商品
     *
     * @param
     * @return
     * @author qqg
     * @date
     */
    public List<TbWeChatProduct> findUpAll() {
        return weChatProductMapper.findUpAll();
    }

    /**
     * 分页工具(商家使用)
     * page：当前页
     * pageSize：页面容量
     * enterpriseid:企业ID
     *
     * @param
     * @param
     * @return
     */
    public PageInfo<TbWeChatProduct> pageSelect(int page, int pageSize, String enterpriseid, String productstatus) {
        //pageHelper帮我们生成分页语句
        PageHelper.startPage(page, pageSize);
        List<TbWeChatProduct> findlist = weChatProductMapper.findAllproduct(enterpriseid, productstatus);
        PageInfo<TbWeChatProduct> pageInfoProductList = new PageInfo<TbWeChatProduct>(findlist);
        System.out.println("pageInfoProductList" + pageInfoProductList);
        return pageInfoProductList;
    }

    @Override
    public List<WeChatProductOutput> findList(List<String> productIdList) {
        return weChatProductMapper.findList(productIdList).stream()
                .map(e -> {
                    WeChatProductOutput output = new WeChatProductOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }

    /**
     * 商家
     * 根据登录的企业id查询所有产品
     * 上架(0),下架(1)分类
     * @param
     * @param enterpriseid
     */
    @Override
    public List<TbWeChatProduct> findAllproduct(String enterpriseid, String productstatus) {
        String __enenterpriseid = null;
        String __productstatus = null;
        if (enterpriseid != null) {
            __enenterpriseid = enterpriseid;
        }
        if (productstatus != null) {
            __productstatus = productstatus;
        }
        return weChatProductMapper.findAllproduct(__enenterpriseid, __productstatus);
    }

    /**
     * 减库存
     * @param decreaseStockInputList
     */
    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

    }


    /**
     * 退单(加库存)
     * @param decreaseStockInputList
     */
    @Transactional
    public Boolean addstock(List<DecreaseStockInput> decreaseStockInputList) {
        List<TbWeChatProduct> productInfoListadd = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            WeChatProductOutput weChatProductOutput = redisService.get(WeChatProductOutputKey.getById, "" + decreaseStockInput.getProductId(), WeChatProductOutput.class);
            System.out.println("脑瓜同");
            //判断商品是否存在
            if (weChatProductOutput == null) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            weChatProductOutput.setStock(weChatProductOutput.getStock() + decreaseStockInput.getNum());
            redisService.set(WeChatProductOutputKey.getById, "" + weChatProductOutput.getId(), weChatProductOutput);
            System.out.println("*****" + weChatProductOutput.getId() + weChatProductOutput.getStock());
            int count = weChatProductMapper.SellupdateWeChatProduct(weChatProductOutput.getId(), weChatProductOutput.getStock());
            if (count < 1) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
        }
        return true;
    }

    /**
     * 下单用（扣库存）
     * @param decreaseStockInputList
     * @return
     */
    @Transactional
    public List<TbWeChatProduct> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<TbWeChatProduct> productInfoList = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Integer productIdList = Integer.parseInt(decreaseStockInput.getProductId());
            TbWeChatProduct productInfo = weChatProductMapper.findById(productIdList);
            //判断商品是否存在
            if (productInfo == null) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // TbWeChatProduct productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getStock() - decreaseStockInput.getNum();
            if (result < 0) {

                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //把减过的库存字段赋值
            productInfo.setStock(result);
            //修改库存信息
            int count = weChatProductMapper.updateWeChatProduct(productInfo);
            if (count < 1) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_UPDATE);
            }
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }


    /**
     * 根据id查询商品
     *
     * @param productIdList
     * @return
     */
    public TbWeChatProduct findById(Integer productIdList) {
        return weChatProductMapper.findById(productIdList);
    }

    /**
     * 根据id删除当前产品信息
     *
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(Integer id) {
        //删除前确认是否有这个产品
        if (weChatProductMapper.findById(id) == null) {
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }
//        //要删除的用户订单产品
//        TbWeChatProduct  Tb= weChatProductMapper.findById(id);
//        System.out.println("//查询次产品库存信息"+Tb);
//        //查询次产品库存信息
//        TbWeChatProduct  TbX= weChatProductMapper.findByTitle(Tb.getTitle());
//        System.out.println("//查询次产品库存信息"+TbX);
//        //实例化一个对象
//        TbWeChatProduct  tbWeChatProduct=new TbWeChatProduct();
//        //删除购物车产品，原产品加库存
//        tbWeChatProduct.setNum(TbX.getNum()+Tb.getNum());
//        tbWeChatProduct.setId(TbX.getId());
//        if(weChatProductMapper.updateWeChatProduct(tbWeChatProduct)>1){
//            System.out.println("修改成功");
//        }
        return weChatProductMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加产品信息
     *
     * @param record
     * @return
     */
    @Override
    public int addTbWeChatProduct(TbWeChatProduct record) {
        //添加前确认是否已经有了这个产品
        record.setEnterpriseid(248);
        record.setInputtime(new Date());
        record.setEndtime(new Date());
        TbWeChatProduct updateWeChatProduct =weChatProductMapper.findByTitle(record.getTitle());
        if(updateWeChatProduct != null){
            //已存在本标题的产品
            throw new ProductException(ResultEnum.PRODUCT_EXIST);
        }
        return weChatProductMapper.addTbWeChatProduct(record);
    }

    /**
     * 修改产品信息
     * @param record
     * @return
     */
    @Override
    public int updateWeChatProduct(TbWeChatProduct record) {
          //修改前确认是否已经存在这个产品
        TbWeChatProduct updateWeChatProduct =weChatProductMapper.findByTitle(record.getTitle());
        if(updateWeChatProduct != null){
            //已存在本标题的产品
            throw new ProductException(ResultEnum.PRODUCT_EXIST);
        }
        return weChatProductMapper.updateWeChatProduct(record);
    }

    /**
     * 修改产品信息
     *
     * @param record
     * @return
     */
    @Override
    public int updateWeChatProduct11(TbWeChatProduct record, String photopath) {
        String path = "F:/idea/file/nswxm/nsw-wx-plat-wechathous/src/main/resources/static/";
        File file = new File(path + photopath);
        System.out.println("photopath:" + photopath);
        if (!photopath.isEmpty() && !record.getPhotopath().equals(photopath)) {
            file.delete();
            System.out.println(file.getName() + "删除成功");
        } else {
            System.out.println(file.getName() + "删除失败");
        }
        return weChatProductMapper.updateWeChatProduct(record);
    }

    /**
     * 根据产品columnid(id)查询信息
     *
     * @param columnid
     * @return
     */
    @Override
    public List<TbWeChatProduct> selectBycolumnid(Integer columnid) {
        return weChatProductMapper.selectBycolumnid(columnid);
    }

    /**
     * 以产品标题查询产品
     * @param title
     * @return
     */
    @Override
    public TbWeChatProduct findByTitle(String title) {
        return weChatProductMapper.findByTitle(title);
    }

    /**
     * 产品查询
     * @return
     */
    @Override
    public List<TbWeChatProduct> isBestlist() {
        return weChatProductMapper.isBestlist();
    }


    @Override
    public List<WeChatProductOutput> findByproductid(List<String> productIdList) {
        return weChatProductMapper.findList(productIdList).stream()
                .map(e -> {
                    WeChatProductOutput output = new WeChatProductOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }

}