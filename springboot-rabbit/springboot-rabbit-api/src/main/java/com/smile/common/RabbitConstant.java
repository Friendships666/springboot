package com.smile.common;

/**
 * @作者 liutao
 * @时间 2020/3/5 23:48
 * @描述  rabbitmq 公共类
 */
public class RabbitConstant {
    /**
     * 生产者交换器
     */
    private static final String PRODUCER_EXCHANGE = "producerExchange";

    /**
     * 消费者交换器
     */
    private static final String CUSTOMER_EXCHANGE = "customerExchange";

    /**
     * 生产者队列
     */
    private static final String PRODUVER_QUEUE = "sb.produce.queue";

    /**
     * 消费者队列
     */
    private static final String CUSTOMER_QUEUE = "sb.customer.queue";

}
