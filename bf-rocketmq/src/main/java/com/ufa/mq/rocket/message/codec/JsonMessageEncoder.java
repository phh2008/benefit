package com.ufa.mq.rocket.message.codec;

import com.alibaba.fastjson.JSON;
import com.ufa.mq.rocket.message.MessageEncoder;

/**
 * json 编码器 by fastJson
 *
 * @param <T>
 */
public class JsonMessageEncoder<T> implements MessageEncoder<T> {

    private static final JsonMessageEncoder instance = new JsonMessageEncoder();

    private JsonMessageEncoder() {
    }

    public static JsonMessageEncoder create() {
        return instance;
    }

    @Override
    public byte[] encode(T message) {
        return JSON.toJSONBytes(message);
    }
}
