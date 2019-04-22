package com.ufa.mq.rocket.message;

/**
 * 消息编码器
 *
 * @param <T>
 */
public interface MessageEncoder<T> {

    byte[] encode(T message);
}
