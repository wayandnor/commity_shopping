package com.nor.cs.mq.constant;

public class MqConst {
    /**
     * 消息补偿
     */
    public static final String MQ_KEY_PREFIX = "cs.mq:list";
    public static final int RETRY_COUNT = 3;

    /**
     * 商品上下架
     */
    public static final String GOODS_EXCHANGE_DIRECT = "cs.goods.direct";
    public static final String GOODS_ROUTING_PUT_ON_SALE = "cs.goods.sale";
    public static final String GOODS_ROUTING_UNSHELVE = "cs.goods.unshelve";
    //队列
    public static final String GOODS_QUEUE_PUT_ON_SALE  = "cs.goods.sale";
    public static final String GOODS_QUEUE_UNSHELVE  = "cs.goods.unshelve";

    /**
     * 团长上下线
     */
    public static final String EXCHANGE_LEADER_DIRECT = "cs.leader.direct";
    public static final String ROUTING_LEADER_UPPER = "cs.leader.upper";
    public static final String ROUTING_LEADER_LOWER = "cs.leader.lower";
    //队列
    public static final String QUEUE_LEADER_UPPER  = "cs.leader.upper";
    public static final String QUEUE_LEADER_LOWER  = "cs.leader.lower";

    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "cs.order.direct";
    public static final String ROUTING_ROLLBACK_STOCK = "cs.rollback.stock";
    public static final String ROUTING_MINUS_STOCK = "cs.minus.stock";

    public static final String ROUTING_DELETE_CART = "cs.delete.cart";
    //解锁普通商品库存
    public static final String QUEUE_ROLLBACK_STOCK = "cs.rollback.stock";
    public static final String QUEUE_SECKILL_ROLLBACK_STOCK = "cs.seckill.rollback.stock";
    public static final String QUEUE_MINUS_STOCK = "cs.minus.stock";
    public static final String QUEUE_DELETE_CART = "cs.delete.cart";

    //支付
    public static final String EXCHANGE_PAY_DIRECT = "cs.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "cs.pay.success";
    public static final String QUEUE_ORDER_PAY  = "cs.order.pay";
    public static final String QUEUE_LEADER_BILL  = "cs.leader.bill";

    //取消订单
    public static final String EXCHANGE_CANCEL_ORDER_DIRECT = "cs.cancel.order.direct";
    public static final String ROUTING_CANCEL_ORDER = "cs.cancel.order";
    //延迟取消订单队列
    public static final String QUEUE_CANCEL_ORDER  = "cs.cancel.order";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "cs.exchange.direct.task";
    public static final String ROUTING_TASK_23 = "cs.task.23";
    //队列
    public static final String QUEUE_TASK_23  = "cs.queue.task.23";
}