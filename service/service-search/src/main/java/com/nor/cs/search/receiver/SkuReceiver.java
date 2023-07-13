package com.nor.cs.search.receiver;

import com.nor.cs.mq.constant.MqConst;
import com.nor.cs.search.service.api.SkuService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class SkuReceiver {
    @Resource
    private SkuService skuService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.GOODS_QUEUE_PUT_ON_SALE,durable = "true"),
            exchange = @Exchange(value = MqConst.GOODS_EXCHANGE_DIRECT),
            key = {MqConst.GOODS_ROUTING_PUT_ON_SALE}
    ))
    public void putSkuOnSale(Long id, Message message, Channel channel) throws IOException {
        if (id != null) {
            skuService.addSkuInfo(id);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.GOODS_QUEUE_UNSHELVE,durable = "true"),
            exchange = @Exchange(value = MqConst.GOODS_EXCHANGE_DIRECT),
            key = {MqConst.GOODS_ROUTING_UNSHELVE}
    ))
    public void unshelveSku(Long id, Message message, Channel channel) throws IOException {
        if (id != null) {
            skuService.removeSkuInfo(id);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
