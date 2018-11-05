//package com.nsw.wx.product.rabbitmq;
//
//import com.nsw.wx.product.common.DecreaseStockInput;
//import com.nsw.wx.product.common.WeChatProductOutput;
//import com.nsw.wx.product.pojo.TbWeChatProduct;
//import com.nsw.wx.product.redis.RedisService;
//import com.nsw.wx.product.server.WeChatProductService;
//import com.nsw.wx.product.server.impl.WeChatProductServiceImpl;
//import com.nsw.wx.product.util.JsonUtil;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Created by IntelliJ IDEA.
// *
// * @author 张维维
// * date: 2018/10/26/026 15:59
// */
//@Component
//public class erduceStockReceover {
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//@Autowired
// private WeChatProductServiceImpl weChatProductService;
//    @RabbitListener(queuesToDeclare = @Queue(MQConfig.REDUCE_STOCK))
//    public void  Orderreceive(String message ) {
//
//        DecreaseStockInputReceiver decreaseStockInputReceiver=RedisService.stringToBean(message, DecreaseStockInputReceiver.class);
//        List<DecreaseStockInput> decreaseStockInputList = decreaseStockInputReceiver.getDecreaseStockInput();
//        List<TbWeChatProduct> tbWeChatProducts = weChatProductService.decreaseStockProcess(decreaseStockInputList);
//        //发送mq消息
//        List<WeChatProductOutput> productInfoOutputList = tbWeChatProducts.stream().map(e -> {
//            WeChatProductOutput output = new WeChatProductOutput();
//            BeanUtils.copyProperties(e, output);
//            return output;
//        }).collect(Collectors.toList());
//        System.out.println("productInfoOutputList"+productInfoOutputList);
//        amqpTemplate.convertAndSend(MQConfig.STOCK_RESULT, JsonUtil.toJson(productInfoOutputList));
//
//    }
//
//}
