package com.ufa.mq.rocket.handler;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 顺序消息处理器
 *
 * @param <T>
 */
public interface OrderlyMessageHandler<T> {

    /**
     * 消息处理
     *
     * @param data
     * @param message
     * @param context
     * @return
     */
    ConsumeOrderlyStatus process(T data, MessageExt message, ConsumeOrderlyContext context);

    /**
     * 解码
     * message.getBody to T
     *
     * @return
     */
    T decode(byte[] message);
}
