package com.ufa.mq.rocket.message;

/**
 * 消息解码器
 *
 * @param <T>
 */
public interface MessageDecoder<T> {

    T decode(byte[] message);
}
