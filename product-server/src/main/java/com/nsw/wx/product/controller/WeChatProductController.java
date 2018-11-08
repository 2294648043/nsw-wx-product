package com.nsw.wx.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.product.VO.ProductVO;
import com.nsw.wx.product.VO.WeChatProductVO;
import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.common.WeChatProductOutput;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.pojo.WeChatProductColumn;
import com.nsw.wx.product.repository.TbWeChatProductRepository;
import com.nsw.wx.product.server.WeChatProductColumnService;
import com.nsw.wx.product.server.WeChatProductService;
import com.nsw.wx.product.util.*;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nsw.wx.product.enums.ResultEnum;
import com.nsw.wx.product.exception.ProductException;
import com.nsw.wx.product.redis.RedisService;
import com.nsw.wx.product.redis.WeChatProductOutputKey;
import com.nsw.wx.product.util.ResultVOUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.Model;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: nsw-wx-product
 * @description: 商品
 * @author: 张维维
 * @create: 2018-10-18 16:40
 **/
@RestController
@RequestMapping("/api/product")
public class WeChatProductController implements InitializingBean{

    @Autowired
    private RedisService redisService;
    @Autowired
    private WeChatProductService weChatProductService;
    @Autowired
    private WeChatProductColumnService weChatProductColumnService;
    @Autowired
    private TbWeChatProductRepository tbWeChatProductRepository;
    //设置集群名称
    Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
    //创建client
    TransportClient client;

    {
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**（展示给用户）
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @RequestMapping("/list")
        public String list(HttpServletResponse response, Model model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
            //1. 查询所有在架的商品
            List<TbWeChatProduct> productInfoList = weChatProductService.findUpAll();
           System.out.println(productInfoList);
            //2. 获取类目type列表
            List<Integer> categoryTypeList = productInfoList.stream()
                    .map(TbWeChatProduct::getColumnid)
                .collect(Collectors.toList());
        System.out.println("categoryTypeList"+categoryTypeList);
        //3. 从数据库查询类目
        List<WeChatProductColumn> categoryList = weChatProductColumnService.findByCategoryTypeIn(categoryTypeList);
        System.out.println("categoryList"+categoryList);
        //4. 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (WeChatProductColumn productCategory : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setTitle(productCategory.getTitle());
            productVO.setId(productCategory.getId());
            List<WeChatProductVO> productInfoVOList = new ArrayList<>();
            for (TbWeChatProduct productInfo : productInfoList) {
                if (productInfo.getColumnid().equals(productCategory.getId())) {
                    WeChatProductVO weChatProduct = new WeChatProductVO();
                    BeanUtils.copyProperties(productInfo, weChatProduct);
                    productInfoVOList.add(weChatProduct);
                }
            }
            productVO.setWeChatProductVOS(productInfoVOList);
            productVOList.add(productVO);
        }
        //String json = JSONArray.toJSONString(productVOList);
        return JSONArray.toJSONString(productVOList);
    }

    /**
     * 获取商品列表(给订单服务用的)
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<WeChatProductOutput> listForOrder(HttpServletResponse response,@RequestBody List<String> productIdList) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        //return weChatProductService.findList(productIdList);
        return weChatProductService.findByproductid(productIdList);
    }


    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(HttpServletResponse response,@RequestBody List<DecreaseStockInput> decreaseStockInputList){
        response.setHeader("Access-Control-Allow-Origin", "*");
        weChatProductService.decreaseStock(decreaseStockInputList);
    }
    /**
     * 加库存(用户退单修改库存)
     * @param decreaseStockInputList
     */
    @PostMapping("/addStock")
    public void addStock(HttpServletResponse response,@RequestBody List<DecreaseStockInput> decreaseStockInputList){
        response.setHeader("Access-Control-Allow-Origin", "*");
       // weChatProductService.decreaseStock(decreaseStockInputList);
        weChatProductService.addstock(decreaseStockInputList);
    }

    /**
     * 商家分页显示产品可根据是否上下架显示
     * @param page
     * @param limit
     * @param enterpriseid
     * @param productstatus
     * @return
     */
    @RequestMapping("/listproduct")
    public Object listproduct(HttpServletResponse response,
                              @RequestParam(value = "page",required = false) String page,
                              @RequestParam(value = "limit",required = false) String limit,
                              @RequestParam(value = "enterpriseid",required = false) String enterpriseid,
                              @RequestParam(value = "productstatus",required = false) String productstatus
                              ){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<TbWeChatProduct> pageInfoList = weChatProductService.pageSelect(
        Integer.parseInt(page),Integer.parseInt(limit),enterpriseid,productstatus);
        List<TbWeChatProduct> productInfoList = pageInfoList.getList();
        List<WeChatProductVO> productInfoVOList = new ArrayList<>();
        for (TbWeChatProduct productInfo : productInfoList){
            WeChatProductVO weChatProduct = new WeChatProductVO();
            BeanUtils.copyProperties(productInfo, weChatProduct);
            productInfoVOList.add(weChatProduct);
        }
        //总数
        long count=pageInfoList.getTotal();
        return JsonData.buildSuccess(count,productInfoList);
    }

    /**
     * (商家)
     * 根据id查询产品信息
     * @param id
     * @return
     */
    @RequestMapping("selectidoneid")
    public Object selectidoneid(HttpServletResponse response,@RequestParam(value = "id",required = false) String id){
        response.setHeader("Access-Control-Allow-Origin", "*");
        TbWeChatProduct tb=weChatProductService.findById(Integer.parseInt(id));
        return weChatProductService.findById(Integer.parseInt(id));
    }

    /**
     * 新增
     * 商家以id删除产品信息
     * @param id()
     * @return
     */
    @RequestMapping("deleteproductid")
    public Object deleteproduct(HttpServletResponse response,@RequestParam("id") Integer id){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //删除搜索引擎里的
        DeleteResponse responses = client.prepareDelete("product", "wechatproductoutput", id.toString())
                .execute()
                .actionGet();
        return ResultVOUtil.success(weChatProductService.deleteByPrimaryKey(id));
    }
    //@RequestBody

    /**
     * 增加方法
     * @param response
     * @param json_str
     * @return
     */
    @RequestMapping("add")
    public Object addproduct(HttpServletResponse response,@RequestBody String json_str){
        response.setHeader("Access-Control-Allow-Origin", "*");
        TbWeChatProduct weChatProduct =   new JsonMap().string2Obj(json_str,new TbWeChatProduct().getClass());
        int count =  weChatProductService.addTbWeChatProduct(weChatProduct);
        //copy
        WeChatProductOutput weChatProductOutput = new WeChatProductOutput();
        //把添加的信息一起添加到搜索引擎中
        try {
            new CopyUtils().Copy(weChatProduct,weChatProductOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbWeChatProductRepository.save(weChatProductOutput);
        return count;
    }

    @RequestMapping("update")
    public Object update(HttpServletResponse response,TbWeChatProduct tbWeChatProduct){
        response.setHeader("Access-Control-Allow-Origin", "*");
        int count=weChatProductService.updateWeChatProduct(tbWeChatProduct);
        System.out.println("--------->"+count);
        return count;
    }

    @RequestMapping("update111")
    public Object update111(HttpServletResponse response,@RequestBody String json_str
            ,@RequestParam(value = "photopath",required = false) String photopath){
        response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println("---------------->"+json_str);
        TbWeChatProduct tbWeChatProduct = new JsonMap().string2Obj(json_str,new TbWeChatProduct().getClass());
        int count = weChatProductService.updateWeChatProduct11(tbWeChatProduct,photopath);
      /*  JSONArray.toJSONString(count)*/
        return count;

    }

    @RequestMapping("addproduct")
    public Object addproduct(HttpServletResponse response,@RequestBody TbWeChatProduct tbWeChatProduct){
        response.setHeader("Access-Control-Allow-Origin", "*");
        TbWeChatProduct tb=weChatProductService.findById(6);
        return weChatProductService.addTbWeChatProduct(tb);
    }
    /**
     * 系统初始化
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
    List<TbWeChatProduct> tbWeChatProducts = weChatProductService.findUpAll();
    if (tbWeChatProducts.isEmpty()){
        throw  new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
    }
        List<WeChatProductOutput> productInfoOutputList = tbWeChatProducts.stream().map(e -> {
            WeChatProductOutput output = new WeChatProductOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        for (WeChatProductOutput weChatProductOutput:productInfoOutputList){

            //放进elasticsearch搜索引擎
            tbWeChatProductRepository.save(weChatProductOutput);
            //放进redis
            redisService.set(WeChatProductOutputKey.getById,""+weChatProductOutput.getId(),weChatProductOutput);
        }
    }

    /**
     *查询产品（isbest最好的//搜索引擎）
     * @return
     */
    @RequestMapping("enGine")
    public Object iBest(HttpServletRequest request){
        String title = request.getParameter("title");
        System.out.println("title=============>"+title);
        QueryBuilder queryBuilder;
        if (title != null && title!=""){
            queryBuilder = QueryBuilders.matchQuery("title", title);
        }else{
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        Iterable<WeChatProductOutput> list =  tbWeChatProductRepository.search(queryBuilder);
        List<WeChatProductOutput> productInfoVOList = new ArrayList<>();
        for (WeChatProductOutput weChatProduct : list){
            //判断是否上架
            if(weChatProduct.getProductStatus()==0) {
                productInfoVOList.add(weChatProduct);
            }else{
                //如果不是上架的商品就删了
                DeleteResponse responses = client.prepareDelete("product", "wechatproductoutput", weChatProduct.getId().toString())
                        .execute()
                        .actionGet();
            }
        }
        return productInfoVOList;
    }


    /**
     *查询产品（isbest最好的）
     * @param response
     * @return
     */
    @RequestMapping("iBest")
    public Object iBest(HttpServletResponse response){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println(weChatProductService.isBestlist());
        List<TbWeChatProduct> productInfoList=weChatProductService.isBestlist();
        List<WeChatProductOutput> productInfoVOList = new ArrayList<>();
        for (TbWeChatProduct productInfo : productInfoList){
            WeChatProductOutput weChatProduct = new WeChatProductOutput();
            BeanUtils.copyProperties(productInfo, weChatProduct);
            productInfoVOList.add(weChatProduct);
        }
        return productInfoVOList;
    }



    @RequestMapping("selectone")
    public Object selectone(HttpServletResponse response,@RequestParam(value = "id") String id){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        WeChatProductOutput weChatProductOutput = redisService.get(WeChatProductOutputKey.getById, "" + id, WeChatProductOutput.class);
        return weChatProductOutput;
    }
}