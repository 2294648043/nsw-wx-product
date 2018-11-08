package com.nsw.wx.product.rabbitmq;

import com.nsw.wx.product.util.FastJsonConvertUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderSender {

	//自动注入RabbitTemplate模板类
	@Autowired
	private RabbitTemplate rabbitTemplate;  

	//回调函数: confirm确认
	final ConfirmCallback confirmCallback = new ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.err.println("correlationData: " + correlationData);
			if(ack){


			} else {



			}
		}
	};
	
	//发送消息方法调用: 构建自定义对象消息
	public void sendOrder(TakeStock takeStock) throws Exception {
		rabbitTemplate.setConfirmCallback(confirmCallback);
		//消息唯一ID
		rabbitTemplate.convertAndSend("order-exchange", "order.AB",FastJsonConvertUtil.convertObjectToJSON(takeStock));
	}
	
}
