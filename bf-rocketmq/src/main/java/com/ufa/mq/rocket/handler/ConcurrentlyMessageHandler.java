package com.ufa.mq.rocket.handler;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 并发消息处理器
 */
public interface ConcurrentlyMessageHandler<T> {

    /**
     * 消息处理
     *
     * @param data
     * @param message
     * @param context
     * @return
     */
    ConsumeConcurrentlyStatus process(T data, MessageExt message, ConsumeConcurrentlyContext context);

    /**
     * 解码
     * message.getBody to T
     *
     * @return
     */
    T decode(byte[] message);
}
