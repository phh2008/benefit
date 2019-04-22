package com.ufa.mq.rocket.handler;

import com.ufa.mq.rocket.message.MessageDecoder;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 顺序消息处理器
 *
 * @param <T>
 */
public interface OrderlyMessageHandler<T> {

    ConsumeOrderlyStatus process(T data, MessageExt message, ConsumeOrderlyContext context);

    MessageDecoder<T> getMessageDecoder();
}
