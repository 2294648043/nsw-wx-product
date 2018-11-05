package com.nsw.wx.product.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.nsw.wx.product.com.nsw.wx.enums.ResultEnum;
import com.nsw.wx.product.exception.ProductException;
import com.nsw.wx.product.mapper.WeChatProductColumnMapper;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import com.nsw.wx.product.server.WeChatProductColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return weChatProductColumnMapper.findByCategoryTypeIn(categoryTypeList);
    }


    /**
     * 查询登录企业显示产品分类信息
     *
     * @param enterpriseid
     * @return
     */
    @Override
    public List<WeChatProductColumn> findAllproductcolumn(String enterpriseid) {
        return weChatProductColumnMapper.findAllproductcolumn(enterpriseid);
    }

    /**
     * 分页显示产品分类信息
     *
     * @param page
     * @param pageSize
     * @param enterpriseid
     * @return
     */
    @Override
    public PageInfo<WeChatProductColumn> pageSelect(int page, int pageSize, String enterpriseid) {
        PageHelper.startPage(page, pageSize);
        List<WeChatProductColumn> findlist = weChatProductColumnMapper.findAllproductcolumn(enterpriseid);
        PageInfo<WeChatProductColumn> pageInfoProductList = new PageInfo<WeChatProductColumn>(findlist);
        return pageInfoProductList;
    }

    /**
     * 根据id删除产品分类信息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer id, String photopath) {
        //删除前确认该类目底下是否含有产品
        String weChatProducts = "[]";
        if (!weChatProductMapper.selectBycolumnid(id).isEmpty()) {
            throw new ProductException(ResultEnum.PRODUCT_COLUMN_EXIST);
        } else {
            String path = "F:/idea/file/nswxm/nsw-wx-plat-wechathous/src/main/resources/static/";
            File file = new File(path + photopath);
            if (photopath != null) {
                file.delete();
                System.out.println(file.getName() + "删除成功15551");
            } else {
                System.out.println(file.getName() + "删除失败5155");
            }
        }
        return weChatProductColumnMapper.deleteByPrimaryKey(id);
    }

    /**
     * 显示全部产品分类
     *
     * @return
     */
    @Override
    public List<WeChatProductColumn> Allproductcolumn() {
        return weChatProductColumnMapper.Allproductcolumn();
    }

    /**
     * 添加产品分类信息
     *
     * @param record
     * @return
     */
    @Override
    public int insertWeChatProductColumn(WeChatProductColumn record) {
        //添加前确认是否已经包含有要添加的类目
        if (weChatProductColumnMapper.findByTitle(record.getTitle()) != null) {
            return 0;
        } else {
            record.setInputtime(new Date());
            return weChatProductColumnMapper.insertWeChatProductColumn(record);
        }
    }


    /**
     * 查询产品分类详细信息
     *
     * @param id
     * @return
     */
    @Override
    public WeChatProductColumn selectByPrimaryKey(Integer id) {
        return weChatProductColumnMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改产品分类详细信息
     *
     * @param record
     * @return
     */
    @Override
    public int updateWeChatProductColumn(WeChatProductColumn record, String photopath) {
        String path = "F:/idea/file/nswxm/nsw-wx-plat-wechathous/src/main/resources/static/";
        File file = new File(path + photopath);
        System.out.println("photopath:" + photopath);
        if (!photopath.isEmpty() && !record.getPhotopath().equals(photopath)) {
            file.delete();
            System.out.println(file.getName() + "删除成功");
        } else {
            System.out.println(file.getName() + "删除失败");
        }
        int count = weChatProductColumnMapper.updateWeChatProductColumn(record);
        System.out.println("--------------->" + count);
        return count;
    }

    /**
     * 根据产品分类标题查询
     *
     * @param title
     * @return
     */
    @Override
    public WeChatProductColumn findByTitle(String title) {
        return weChatProductColumnMapper.findByTitle(title);
    }

    /**
     * 新增
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, Object> photopath(MultipartFile file) throws IOException {
        //如果文件内容不为空，则写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            String path = "F:/idea/file/nswxm/nsw-wx-plat-wechathous/src/main/resources/static/upload";
            System.out.println("文件名称" + file.getOriginalFilename());
              //上传文件名
            String date = new Date().toString().replaceAll("[[\\s-:punct:]]", "");
            String filename = date + file.getOriginalFilename();
            File filepath = new File(path, filename);

            //判断路径是否存在，没有就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            //将上传文件保存到一个目标文档中
            File file1 = new File(path + File.separator + filename);
            file.transferTo(file1);
            Map<String, Object> res = new HashMap<>();
            //返回的是一个url对象
            res.put("url", "upload/" + filename);
            System.out.println("7777777777777777777");
            return res;
        } else {
            System.out.println("出现异常...........");
            return null;
        }

    }

    @Override
    public List<WeChatProductColumn> SelTitle(Integer enterpriseid) {

        return weChatProductColumnMapper.SelTitle(enterpriseid);
    }
}
