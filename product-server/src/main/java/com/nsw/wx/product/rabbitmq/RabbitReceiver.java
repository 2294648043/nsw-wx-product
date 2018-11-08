package com.nsw.wx.product.rabbitmq;

import java.util.List;
import java.util.Map;

import com.nsw.wx.product.enums.ResultEnum;
import com.nsw.wx.product.common.DecreaseStockInput;
import com.nsw.wx.product.exception.ProductException;
import com.nsw.wx.product.mapper.WeChatProductMapper;
import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.util.FastJsonConvertUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitReceiver {

	@Autowired
	private WeChatProductMapper weChatProductMapper;
	@Autowired
	private  RabbitOrderSender rabbitOrderSender;

//	@RabbitHandler
//	public void onMessage(Message message, Channel channel) throws Exception {
//		System.err.println("--------------------------------------");
//		System.err.println("消费端Payload: " + message.getPayload());
//		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//		//手工ACK
//		channel.basicAck(deliveryTag, false);
//	}
//
//
	/**
	 * 
	 * 	spring.rabbitmq.listener.order.queue.name=queue-2
		spring.rabbitmq.listener.order.queue.durable=true
		spring.rabbitmq.listener.order.exchange.name=exchange-1
		spring.rabbitmq.listener.order.exchange.durable=true
		spring.rabbitmq.listener.order.exchange.type=topic
		spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
		spring.rabbitmq.listener.order.key=springboot.*
	 * @param
	 * @param channel
	 * @throws Exception
	 */
//	@RabbitListener(bindings = @QueueBinding(
//			value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
//			durable="${spring.rabbitmq.listener.order.queue.durable}"),
//			exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
//			durable="${spring.rabbitmq.listener.order.exchange.durable}",
//			type= "${spring.rabbitmq.listener.order.exchange.type}",
//			ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
//			key = "${spring.rabbitmq.listener.order.key}"
//			)
//	)
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-20",
					durable="false"),
			exchange = @Exchange(value = "test-order20",
					durable="false",
					type= "topic",
					ignoreDeclarationExceptions = "true"),
			key = "test.*"
	)
	)
	@RabbitHandler
	public void onOrderMessage(String decreaseStockInputReceiver,
							   Channel channel,
							   @Headers Map<String, Object> headers) throws Exception {

  	    DecreaseStockInputReceiver DecreaseStockInputReceiver = FastJsonConvertUtil.convertJSONToObject(decreaseStockInputReceiver, DecreaseStockInputReceiver.class);

		List<DecreaseStockInput> decreaseStockInputList = DecreaseStockInputReceiver.getDecreaseStockInput();

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
				TakeStock takeStock = new TakeStock(false, DecreaseStockInputReceiver.getOrderId());
				rabbitOrderSender.sendOrder(takeStock);
				throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
			}

			//把减过的库存字段赋值
			productInfo.setStock(result);
			//修改库存信息
			int count = weChatProductMapper.updateWeChatProduct(productInfo);
			if (count < 1) {
				TakeStock takeStock = new TakeStock(false, DecreaseStockInputReceiver.getOrderId());
				rabbitOrderSender.sendOrder(takeStock);
				throw new ProductException(ResultEnum.PRODUCT_STOCK_UPDATE);
			}

		}
		TakeStock takeStock = new TakeStock(true, DecreaseStockInputReceiver.getOrderId());
		rabbitOrderSender.sendOrder(takeStock);
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
}
