package com.ufa.mq.rocket.message.codec;

import com.alibaba.fastjson.JSON;
import com.ufa.mq.rocket.message.MessageDecoder;

/**
 * json 解码器 by fastJson
 *
 * @param <T>
 */
public class JsonMessageDecoder<T> implements MessageDecoder<T> {

    private Class<T> resultClass;

    public JsonMessageDecoder(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public T decode(byte[] message) {
        return JSON.parseObject(message, resultClass);
    }
}
